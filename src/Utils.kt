import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

/**
 * Creates a scanner from the input txt file.
 */
fun scanner(name: String) = Scanner(File("src", "$name.txt"))

/**
 * Reads lines from the given scanner.
 */
fun readLines(scanner: Scanner): List<String> {
    return buildList<String> {
        while (scanner.hasNextLine()) add(scanner.nextLine())
    }
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String): List<String> {
    val scanner = scanner(name)
    return readLines(scanner)
}

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
