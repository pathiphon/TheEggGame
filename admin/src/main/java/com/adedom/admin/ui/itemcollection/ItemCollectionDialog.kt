package com.adedom.admin.ui.itemcollection

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.adedom.admin.R
import com.adedom.admin.util.BaseDialogFragment
import com.adedom.library.extension.dialogFragment

class ItemCollectionDialog : BaseDialogFragment({ R.layout.dialog_item_collection }) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        init(bView)
        return AlertDialog.Builder(activity!!)
            .dialogFragment(bView, R.drawable.ic_egg, R.string.item_collection)
    }

    private fun init(view: View) {

    }
}
