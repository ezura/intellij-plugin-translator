package util

class CommentConverter {

    fun extractTextFromComment(commentText: String): String {
        return commentText
            // For multiline comments, remove "//", "///" at line start.
            .replace(Regex("(^|\n) *?///?"), "")
            .replace("\n", " ")
    }
}