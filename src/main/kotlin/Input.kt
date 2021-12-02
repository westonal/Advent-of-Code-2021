import kotlin.reflect.KClass

fun <T : Any> KClass<T>.input() =
    this.java.getResource("${this.simpleName}Input.txt")!!.openStream().bufferedReader()


fun <T : Any, BlockOutput> KClass<T>.useInput(block: (Sequence<String>) -> BlockOutput): BlockOutput =
        input().useLines(block)
