package com.adedom.android.presentation.signup

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.setVisibility
import com.adedom.android.util.snackbar
import com.adedom.teg.presentation.signup.SignUpViewModel
import com.adedom.teg.util.TegConstant
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private val viewModel by viewModel<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe { state ->
            animationViewLoading.setVisibility(state.loading)

            tvBirthDate.text = state.birthDateString

            btSignUp.isClickable = state.isClickable
        }

        viewModel.signUpEvent.observe { response ->
            if (response.success) {
                findNavController().navigate(R.id.action_signUpFragment_to_imageProfileFragment)
            } else {
                requireView().snackbar(response.message)
            }
        }

        viewModel.attachFirstTime.observe {
            // gender
            viewModel.setStateGender(TegConstant.GENDER_MALE)

            // birth date
            val calendar = Calendar.getInstance()
            viewModel.setStateBirthDate(calendar)
        }

        viewModel.error.observeError()

        etUsername.addTextChangedListener { viewModel.setStateUsername(it.toString()) }
        etPassword.addTextChangedListener { viewModel.setStatePassword(it.toString()) }
        etRePassword.addTextChangedListener { viewModel.setStateRePassword(it.toString()) }
        etName.addTextChangedListener { viewModel.setStateName(it.toString()) }
        rbMale.setOnClickListener { viewModel.setStateGender(TegConstant.GENDER_MALE) }
        rbFemale.setOnClickListener { viewModel.setStateGender(TegConstant.GENDER_FEMALE) }

        // event
        ivBirthDate.setOnClickListener {
            context?.let {
                val calendar = Calendar.getInstance()

                val dpd = DatePickerDialog(
                    it,
                    { _, year, month, dayOfMonth ->
                        calendar.set(year, month, dayOfMonth)
                        viewModel.setStateBirthDate(calendar)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )

                dpd.show()
            }
        }

        btSignUp.setOnClickListener {
            viewModel.callSignUp()
        }

        btBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}
