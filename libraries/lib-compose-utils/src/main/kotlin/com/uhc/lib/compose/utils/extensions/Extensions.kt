package com.uhc.lib.compose.utils.extensions

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Modifier.sharedElementIfAvailable(
    key: String,
    shared: SharedTransitionScope?,
    animatedVisibilityScope: AnimatedVisibilityScope?
): Modifier {
    if (shared == null || animatedVisibilityScope == null) return this

    return with(shared) {
        this@sharedElementIfAvailable.sharedElement(
            rememberSharedContentState(key),
            animatedVisibilityScope
        )
    }
}
