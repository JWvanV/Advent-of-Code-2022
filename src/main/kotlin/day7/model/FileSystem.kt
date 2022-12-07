package day7.model

data class FileSystem(
    private val rootNode: Node.Directory = Node.Directory(parent = null, "/"),
    private val nodes: MutableMap<String, Node> = mutableMapOf(),
) {
    //
    // Getters
    //

    fun getRootDirectory() = rootNode

    fun getDirectory(path: String) = nodes[path] as Node.Directory

    fun getDirectories() = nodes.values.filterIsInstance<Node.Directory>()

    //
    // Setters
    //

    fun addNode(node: Node) {
        nodes[node.getPathName()] = node
    }
}