package com.candybytes.taco.ui.util

import android.view.View
import android.widget.SearchView

fun clearFocusAndCollapseSearchView(view: View?, searchView: SearchView) {

    // Hides keyboard
    view?.hideKeyboard()

    // Removes focus from searchView
    searchView.clearFocus()

    // After clicking cross (x), searchView bar collapses & goes back to original position
    searchView.onActionViewCollapsed()
}

