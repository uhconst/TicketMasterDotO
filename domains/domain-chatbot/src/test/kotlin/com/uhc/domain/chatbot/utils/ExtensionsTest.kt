package com.uhc.domain.chatbot.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ExtensionsTest {

    @Test
    fun `cleanMarkdown WITH bold text THEN removes double asterisks`() {
        val input = "This is **bold** text"
        val expected = "This is bold text"
        assertThat(input.cleanMarkdown()).isEqualTo(expected)
    }

    @Test
    fun `cleanMarkdown WITH italic text THEN removes single asterisks`() {
        val input = "This is *italic* text"
        val expected = "This is italic text"
        assertThat(input.cleanMarkdown()).isEqualTo(expected)
    }

    @Test
    fun `cleanMarkdown WITH mixed bold and italic THEN removes all asterisks`() {
        val input = "This is **bold** and *italic* and ***both***"
        val expected = "This is bold and italic and both"
        assertThat(input.cleanMarkdown()).isEqualTo(expected)
    }

    @Test
    fun `cleanMarkdown WITH multiple occurrences THEN removes all asterisks`() {
        val input = "**First** and **Second**"
        val expected = "First and Second"
        assertThat(input.cleanMarkdown()).isEqualTo(expected)
    }

    @Test
    fun `cleanMarkdown WITH no markdown THEN returns same string`() {
        val input = "Plain text without any stars"
        assertThat(input.cleanMarkdown()).isEqualTo(input)
    }

    @Test
    fun `cleanMarkdown WITH empty string THEN returns empty string`() {
        val input = ""
        assertThat(input.cleanMarkdown()).isEqualTo("")
    }

    @Test
    fun `cleanMarkdown WITH multiple asterisks more than 2 THEN removes them all`() {
        val input = "****Four stars****"
        val expected = "Four stars"
        assertThat(input.cleanMarkdown()).isEqualTo(expected)
    }
}
