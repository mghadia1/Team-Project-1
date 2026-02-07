package util;

// Copyright (c) 2006 Damien Miller <djm@mindrot.org>
//
// Permission to use, copy, modify, and distribute this software for any
// purpose with or without fee is hereby granted, provided that the above
// copyright notice and this permission notice appear in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
// WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
// ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
// ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
// OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

/**
 * BCrypt implements OpenBSD-style Blowfish password hashing using
 * the scheme described in "A Future-Adaptable Password Scheme" by
 * Niels Provos and David Mazieres.
 * <p>
 * This password hashing system tries to thwart off-line password
 * cracking using a computationally-intensive hashing algorithm,
 * based on Bruce Schneier's Blowfish cipher. The work factor of
 * the algorithm is parameterised, so it can be increased as
 * computers get faster.
 * <p>
 * Usage is really simple. To hash a password for the first time,
 * call the hashpw method with a random salt, like this:
 * <p>
 * <code>
 * String pw_hash = BCrypt.hashpw(plain_password, BCrypt.gensalt()); <br />
 * </code>
 * <p>
 * To check whether a plaintext password matches one that has been
 * hashed previously, use the checkpw method:
 * <p>
 * <code>
 * if (BCrypt.checkpw(candidate_password, stored_hash))<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;System.out.println("It matches");<br />
 * else<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;System.out.println("It does not match");<br />
 * </code>
 * <p>
 * The gensalt() method takes an optional parameter (log_rounds)
 * that determines the computational complexity of the hashing:
 * <p>
 * <code>
 * String strong_salt = BCrypt.gensalt(10)<br />
 * String stronger_salt = BCrypt.gensalt(12)<br />
 * </code>
 * <p>
 * The amount of work increases exponentially (2**log_rounds), so 
 * each increment is twice as much work. The default log_rounds is
 * 10, and the valid range is 4 to 31.
 *
 * @author Damien Miller
 * @version 0.4
 */
public class BCrypt {
	// BCrypt parameters
	private static final int GENSALT_DEFAULT_LOG2_ROUNDS = 10;
	private static final int BCRYPT_SALT_LEN = 16;

	// Blowfish parameters
	private static final int BLOWFISH_NUM_ROUNDS = 16;

	// Initial contents of key schedule
	private static final int P_orig[] = {
		0x243f6a88, 0x85a308d3, 0x13198a2e, 0x03707344,
		0xa4093822, 0x299f31d0, 0x082efa98, 0xec4e6c89,
		0x452821e6, 0x38d01377, 0xbe5466cf, 0x34e90c6c,
		0xc0ac29b7, 0xc97c50dd, 0x3f84d5b5, 0xb5470917,
		0x9216d5d9, 0x8979fb1b
	};
	private static final int S_orig[] = {
		0xd1310ba6, 0x98dfb5ac, 0x2ffd72db, 0xd01adfb7,
		0xb8e1afed, 0x6a267e96, 0xba7c9045, 0xf12c7f99,
		0x24a19947, 0xb3916cf7, 0x0801f2e2, 0x858efc16,
		0x636920d8, 0x71574e69, 0xa458fea3, 0xf4933d7e,
		0x0d95748f, 0x728eb658, 0x718bcd58, 0x82154aee,
		0x7b54a41d, 0xc25a59b5, 0x9c30d539, 0x2af26013,
		0xc5d1b023, 0x286085f0, 0xca417918, 0xb8db38ef,
		0x8e79dcb0, 0x603a180e, 0x6c9e0e8b, 0xb01e8a3e,
		0xd71577c1, 0xbd314b27, 0x78af2fda, 0x55605c60,
		0xe65525f3, 0xaa55ab94, 0x57489862, 0x63e81440,
		0x55ca396a, 0x2aab10b6, 0xb4cc5c34, 0x1141e8ce,
		0xa15486af, 0x7c72e993, 0xb3ee1411, 0x636fbc2a,
		0x2ba9c55d, 0x741831f6, 0xce5c3e16, 0x9b87931e,
		0xafd6ba33, 0x6c24cf5c, 0x7a325381, 0x28958677,
		0x3b8f4898, 0x6b4bb9af, 0xc4bfe81b, 0x66282193,
		0x61d809cc, 0xfb21a991, 0x487cac60, 0x5dec8032,
		0xef845d5d, 0xe98575b1, 0xdc262302, 0xeb651b88,
		0x23893e81, 0xd396acc5, 0x0f6d6ff3, 0x83f44239,
		0x2e0b4482, 0xa4842004, 0x69c8f04a, 0x9e1f9b5e,
		0x21c66842, 0xf6e96c9a, 0x670c9c61, 0xabd388f0,
		0x6a51a0d2, 0xd8542f68, 0x960fa728, 0xab5133a3,
		0x6eef0b6c, 0x137a3be4, 0xba3bf050, 0x7efb2a98,
		0xa1f1651d, 0x39af0176, 0x66ca593e, 0x82430e88,
		0x8cee8619, 0x456f9fb4, 0x7d84a5c3, 0x3b8b5ebe,
		0xe06f75d8, 0x85c12073, 0x401a449f, 0x56c16aa6,
		0x4ed3aa62, 0x363f7706, 0x1bfedf72, 0x429b023d,
		0x37d0d724, 0xd00a1248, 0xdb0fead3, 0x49f1c09b,
		0x075372c9, 0x80991b7b, 0x25d479d8, 0xf6e8def7,
		0xe3fe501a, 0xb6794c3b, 0x976ce0bd, 0x04c006ba,
		0xc1a94fb6, 0x409f60c4, 0x5e5c9ec2, 0x196a2463,
		0x68fb6faf, 0x3e6c53b5, 0x1339b2eb, 0x3b52ec6f,
		0x6dfc511f, 0x9b30952c, 0xcc814544, 0xaf5ebd09,
		0xbee3d004, 0xde334afd, 0x660f2807, 0x192e4bb3,
		0xc0cba857, 0x45c8740f, 0xd20b5f39, 0xb9d3fbdb,
		0x5579c0bd, 0x1a60320a, 0xd6a100c6, 0x402c7279,
		0x679f25fe, 0xfb1fa3cc, 0x8ea5e9f8, 0xdb3222f8,
		0x3c7516df, 0xfd616b15, 0x2f501ec8, 0xad0552ab,
		0x323db5fa, 0xfd238760, 0x53317b48, 0x3e00df82,
		0x9e5c57bb, 0xca6f8ca0, 0x1a87562e, 0xdf1769db,
		0xd542a8f6, 0x287effc3, 0xac6732c6, 0x8c4f5573,
		0x695b27b0, 0xbbca58c8, 0xe1ffa35d, 0xb8f011a0,
		0x10fa3d98, 0xfd2183b8, 0x4afcb56c, 0x2dd1d35b,
		0x9a53e479, 0xb6f84565, 0xd28e49bc, 0x4bfb9790,
		0xe1ddf2da, 0xa4cb7e33, 0x62fb1341, 0xcee4c6e8,
		0xef20cada, 0x36774c01, 0xd07e9efe, 0x2bf11fb4,
		0x95dbda4d, 0xae909198, 0xeaad8e71, 0x6b93d5a0,
		0xd08ed1d0, 0xafc725e0, 0x8e3c5b2f, 0x8e7594b7,
		0x8ff6e2fb, 0xf2122b64, 0x8888b812, 0x900df01c,
		0x4fad5ea0, 0x688fc31c, 0xd1cff191, 0xb3a8c1ad,
		0x2f2f2218, 0xbe0e1777, 0xea752dfe, 0x8b021fa1,
		0xe5a0cc0f, 0xb56f74e8, 0x18acf3d6, 0xce89e299,
		0xb4a84fe0, 0xfd13e0b7, 0x7cc43b81, 0xd2ada8d9,
		0x165fa266, 0x80957705, 0x93cc7314, 0x211a1477,
		0xe6ad2065, 0x77b5fa86, 0xc75442f5, 0xfb9d35cf,
		0xebcdaf0c, 0x7b3e89a0, 0xd6411bd3, 0xae1e7e49,
		0x00250e2d, 0x2071b35e, 0x226800bb, 0x57b8e0af,
		0x2464369b, 0xf009b91e, 0x5563911d, 0x59dfa6aa,
		0x78c14389, 0xd95a537f, 0x207d5ba2, 0x02e5b9c5,
		0x83260376, 0x6295cfa9, 0x11c81968, 0x4e734a41,
		0xb3472dca, 0x7b14a94a, 0x1b510052, 0x9a532915,
		0xd60f573f, 0xbc9bc6e4, 0x2b60a476, 0x81e67400,
		0x08ba6fb5, 0x571be91f, 0xf296ec6b, 0x2a0dd915,
		0xb6636521, 0xe7b9f9b6, 0xff34052e, 0xc5855664,
		0x53b02d5d, 0xa99f8fa1, 0x08ba4799, 0x6e85076a
	};

	// BCrypt character set for encoding
	static private final char base64_code[] = {
		'.', '/', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
		'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
		'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
		'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
		'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9'
	};

	// Base64 decoding table
	static private final byte index_64[] = {
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, 0, 1, 54, 55,
		56, 57, 58, 59, 60, 61, 62, 63, -1, -1,
		-1, -1, -1, -1, -1, 2, 3, 4, 5, 6,
		7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
		17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
		-1, -1, -1, -1, -1, -1, 28, 29, 30,
		31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
		41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
		51, 52, 53, -1, -1, -1, -1, -1
	};

	// Expanded Blowfish key
	private int P[];
	private int S[];

	/**
	 * Encode a byte array using bcrypt's slightly-modified base64
	 * encoding scheme. Note that this is *not* compatible with
	 * the standard MIME-base64 encoding.
	 *
	 * @param d	the byte array to encode
	 * @param len	the number of bytes to encode
	 * @return	base64-encoded string
	 * @exception IllegalArgumentException if the length is invalid
	 */
	private static String encode_base64(byte d[], int len)
			throws IllegalArgumentException {
		int off = 0;
		StringBuffer rs = new StringBuffer();
		int c1, c2;

		if (len <= 0 || len > d.length)
			throw new IllegalArgumentException ("Invalid len");

		while (off < len) {
			c1 = d[off++] & 0xff;
			rs.append(base64_code[(c1 >> 2) & 0x3f]);
			c1 = (c1 & 0x03) << 4;
			if (off >= len) {
				rs.append(base64_code[c1 & 0x3f]);
				break;
			}
			c2 = d[off++] & 0xff;
			c1 |= (c2 >> 4) & 0x0f;
			rs.append(base64_code[c1 & 0x3f]);
			c1 = (c2 & 0x0f) << 2;
			if (off >= len) {
				rs.append(base64_code[c1 & 0x3f]);
				break;
			}
			c2 = d[off++] & 0xff;
			c1 |= (c2 >> 6) & 0x03;
			rs.append(base64_code[c1 & 0x3f]);
			rs.append(base64_code[c2 & 0x3f]);
		}
		return rs.toString();
	}

	/**
	 * Look up the 3 bits base64-encoded by the specified character,
	 * range-checking againt conversion table
	 * @param x	the base64-encoded value
	 * @return	the decoded value of x
	 */
	private static byte char64(char x) {
		if ((int)x < 0 || (int)x > index_64.length)
			return -1;
		return index_64[(int)x];
	}

	/**
	 * Decode a string encoded using bcrypt's base64 scheme to a
	 * byte array. Note that this is *not* compatible with
	 * the standard MIME-base64 encoding.
	 * @param s	the string to decode
	 * @param maxolen	the maximum number of bytes to decode
	 * @return	an array containing the decoded bytes
	 * @throws IllegalArgumentException if maxolen is invalid
	 */
	private static byte[] decode_base64(String s, int maxolen)
			throws IllegalArgumentException {
		StringBuffer rs = new StringBuffer();
		int off = 0, slen = s.length(), olen = 0;
		byte ret[];
		byte c1, c2, c3, c4, o;

		if (maxolen <= 0)
			throw new IllegalArgumentException ("Invalid maxolen");

		while (off < slen - 1 && olen < maxolen) {
			c1 = char64(s.charAt(off++));
			c2 = char64(s.charAt(off++));
			if (c1 == -1 || c2 == -1)
				break;
			o = (byte)(c1 << 2);
			o |= (c2 & 0x30) >> 4;
			rs.append((char)o);
			if (++olen >= maxolen || off >= slen)
				break;
			c3 = char64(s.charAt(off++));
			if (c3 == -1)
				break;
			o = (byte)((c2 & 0x0f) << 4);
			o |= (c3 & 0x3c) >> 2;
			rs.append((char)o);
			if (++olen >= maxolen || off >= slen)
				break;
			c4 = char64(s.charAt(off++));
			o = (byte)((c3 & 0x03) << 6);
			o |= c4;
			rs.append((char)o);
			++olen;
		}

		ret = new byte[olen];
		for (off = 0; off < olen; off++)
			ret[off] = (byte)rs.charAt(off);
		return ret;
	}

	/**
	 * Blowfish encipher a single 64-bit block encoded as
	 * two 32-bit halves
	 * @param lr	an array containing the two 32-bit half blocks
	 * @param off	the position in the array of the blocks
	 */
	private final void encipher(int lr[], int off) {
		int i, n, l = lr[off], r = lr[off + 1];

		l ^= P[0];
		for (i = 0; i <= BLOWFISH_NUM_ROUNDS - 2;) {
			// Feistel substitution on left word
			n = S[(l >> 24) & 0xff];
			n += S[0x100 | ((l >> 16) & 0xff)];
			n ^= S[0x200 | ((l >> 8) & 0xff)];
			n += S[0x300 | (l & 0xff)];
			r ^= n ^ P[++i];

			// Feistel substitution on right word
			n = S[(r >> 24) & 0xff];
			n += S[0x100 | ((r >> 16) & 0xff)];
			n ^= S[0x200 | ((r >> 8) & 0xff)];
			n += S[0x300 | (r & 0xff)];
			l ^= n ^ P[++i];
		}
		lr[off] = r ^ P[BLOWFISH_NUM_ROUNDS + 1];
		lr[off + 1] = l;
	}

	/**
	 * Cycically extract a word of key material
	 * @param data	the string to extract the data from
	 * @param offp	a "pointer" (as a one-entry array) to the
	 * current offset into data
	 * @return	the next word of material from data
	 */
	private static int streamtoword(byte data[], int offp[]) {
		int i;
		int word = 0;
		int off = offp[0];

		for (i = 0; i < 4; i++) {
			word = (word << 8) | (data[off] & 0xff);
			off = (off + 1) % data.length;
		}

		offp[0] = off;
		return word;
	}

	/**
	 * Initialise the Blowfish key schedule
	 */
	private void init_key() {
		P = (int[])P_orig.clone();
		S = (int[])S_orig.clone();
	}

	/**
	 * Key the Blowfish cipher
	 * @param key	an array containing the key
	 */
	private void key(byte key[]) {
		int i;
		int koffp[] = { 0 };
		int lr[] = { 0, 0 };
		int plen = P.length, slen = S.length;

		for (i = 0; i < plen; i++)
			P[i] = P[i] ^ streamtoword(key, koffp);

		for (i = 0; i < plen; i += 2) {
			encipher(lr, 0);
			P[i] = lr[0];
			P[i + 1] = lr[1];
		}

		for (i = 0; i < slen; i += 2) {
			encipher(lr, 0);
			S[i] = lr[0];
			S[i + 1] = lr[1];
		}
	}

	/**
	 * Perform the "enhanced key schedule" step described by
	 * Provos and Mazieres in "A Future-Adaptable Password Scheme"
	 * http://www.openbsd.org/papers/bcrypt-paper.ps
	 * @param data	salt information
	 * @param key	password information
	 */
	private void ekskey(byte data[], byte key[]) {
		int i;
		int koffp[] = { 0 }, doffp[] = { 0 };
		int lr[] = { 0, 0 };
		int plen = P.length, slen = S.length;

		for (i = 0; i < plen; i++)
			P[i] = P[i] ^ streamtoword(key, koffp);

		for (i = 0; i < plen; i += 2) {
			lr[0] ^= streamtoword(data, doffp);
			lr[1] ^= streamtoword(data, doffp);
			encipher(lr, 0);
			P[i] = lr[0];
			P[i + 1] = lr[1];
		}

		for (i = 0; i < slen; i += 2) {
			lr[0] ^= streamtoword(data, doffp);
			lr[1] ^= streamtoword(data, doffp);
			encipher(lr, 0);
			S[i] = lr[0];
			S[i + 1] = lr[1];
		}
	}

	/**
	 * Perform the central password hashing step in the
	 * bcrypt scheme
	 * @param password	the password to hash
	 * @param salt	the binary salt to hash with the password
	 * @param log_rounds	the binary logarithm of the number
	 * of rounds of hashing to apply
	 * @return	an array containing the binary hashed password
	 */
	private byte[] crypt_raw(byte password[], byte salt[], int log_rounds) {
		int rounds, i, j;
		int cdata[] = (int[])bf_crypt_ciphertext.clone();
		int clen = cdata.length;
		byte ret[];

		if (log_rounds < 4 || log_rounds > 31)
			throw new IllegalArgumentException ("Bad number of rounds");
		rounds = 1 << log_rounds;
		if (salt.length != BCRYPT_SALT_LEN)
			throw new IllegalArgumentException ("Bad salt length");

		init_key();
		ekskey(salt, password);
		for (i = 0; i < rounds; i++) {
			key(password);
			key(salt);
		}

		for (i = 0; i < 64; i++) {
			for (j = 0; j < (clen >> 1); j++)
				encipher(cdata, j << 1);
		}

		ret = new byte[clen * 4];
		for (i = 0, j = 0; i < clen; i++) {
			ret[j++] = (byte)((cdata[i] >> 24) & 0xff);
			ret[j++] = (byte)((cdata[i] >> 16) & 0xff);
			ret[j++] = (byte)((cdata[i] >> 8) & 0xff);
			ret[j++] = (byte)(cdata[i] & 0xff);
		}
		return ret;
	}

	/**
	 * Hash a password using the OpenBSD bcrypt scheme
	 * @param password	the password to hash
	 * @param salt	the salt to hash with (perhaps generated
	 * using BCrypt.gensalt)
	 * @return	the hashed password
	 */
	public static String hashpw(String password, String salt) {
		BCrypt B;
		String real_salt;
		byte passwordb[], saltb[], hashed[];
		char minor = (char)0;
		int rounds, off = 0;
		StringBuffer rs = new StringBuffer();

		if (salt.charAt(0) != '$' || salt.charAt(1) != '2')
			throw new IllegalArgumentException ("Invalid salt version");
		if (salt.charAt(2) == '$')
			off = 3;
		else {
			minor = salt.charAt(2);
			if (minor != 'a' || salt.charAt(3) != '$')
				throw new IllegalArgumentException ("Invalid salt revision");
			off = 4;
		}

		// Extract number of rounds
		if (salt.charAt(off + 2) > '$')
			throw new IllegalArgumentException ("Missing salt rounds");
		rounds = Integer.parseInt(salt.substring(off, off + 2));

		real_salt = salt.substring(off + 3, off + 25);
		try {
			passwordb = (password + (minor >= 'a' ? "\000" : "")).getBytes("UTF-8");
		} catch (UnsupportedEncodingException uee) {
			throw new AssertionError("UTF-8 is not supported");
		}

		saltb = decode_base64(real_salt, BCRYPT_SALT_LEN);

		B = new BCrypt();
		hashed = B.crypt_raw(passwordb, saltb, rounds);

		rs.append("$2");
		if (minor >= 'a')
			rs.append(minor);
		rs.append("$");
		if (rounds < 10)
			rs.append("0");
		rs.append(Integer.toString(rounds));
		rs.append("$");
		rs.append(encode_base64(saltb, saltb.length));
		rs.append(encode_base64(hashed, bf_crypt_ciphertext.length * 4 - 1));
		return rs.toString();
	}

	/**
	 * Generate a salt for use with the BCrypt.hashpw() method
	 * @param log_rounds	the log2 of the number of rounds of
	 * hashing to apply - the work factor therefore increases as
	 * 2**log_rounds.
	 * @param random		an instance of SecureRandom to use
	 * @return	an encoded salt value
	 * @exception IllegalArgumentException	if log_rounds is invalid
	 */
	public static String gensalt(int log_rounds, SecureRandom random) {
		StringBuffer rs = new StringBuffer();
		byte rnd[] = new byte[BCRYPT_SALT_LEN];

		random.nextBytes(rnd);

		rs.append("$2a$");
		if (log_rounds < 10)
			rs.append("0");
		if (log_rounds > 30) {
			throw new IllegalArgumentException ("log_rounds exceeds maximum (30)");
		}
		rs.append(Integer.toString(log_rounds));
		rs.append("$");
		rs.append(encode_base64(rnd, rnd.length));
		return rs.toString();
	}

	/**
	 * Generate a salt for use with the BCrypt.hashpw() method
	 * @param log_rounds	the log2 of the number of rounds of
	 * hashing to apply - the work factor therefore increases as
	 * 2**log_rounds.
	 * @return	an encoded salt value
	 * @exception IllegalArgumentException	if log_rounds is invalid
	 */
	public static String gensalt(int log_rounds) {
		return gensalt(log_rounds, new SecureRandom());
	}

	/**
	 * Generate a salt for use with the BCrypt.hashpw() method,
	 * selecting a reasonable default for the number of hashing
	 * rounds to apply
	 * @return	an encoded salt value
	 */
	public static String gensalt() {
		return gensalt(GENSALT_DEFAULT_LOG2_ROUNDS);
	}

	/**
	 * Check that a plaintext password matches a previously hashed
	 * one
	 * @param plaintext	the plaintext password to verify
	 * @param hashed	the previously-hashed password
	 * @return	true if the passwords match, false otherwise
	 */
	public static boolean checkpw(String plaintext, String hashed) {
		byte hashed_bytes[];
		byte try_bytes[];
		try {
			String try_pw = hashpw(plaintext, hashed);
			hashed_bytes = hashed.getBytes("UTF-8");
			try_bytes = try_pw.getBytes("UTF-8");
		} catch (UnsupportedEncodingException uee) {
			return false;
		}
		if (hashed_bytes.length != try_bytes.length)
			return false;
		byte ret = 0;
		for (int i = 0; i < try_bytes.length; i++)
			ret |= hashed_bytes[i] ^ try_bytes[i];
		return ret == 0;
	}

	private static final int bf_crypt_ciphertext[] = {
		0x4f727068, 0x65616e42, 0x65686f6c,
		0x64657253, 0x63727944, 0x6f756274
	};
}
