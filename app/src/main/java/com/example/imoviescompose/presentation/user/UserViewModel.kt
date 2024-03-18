package com.example.imoviescompose.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imoviescompose.domain.model.user.User
import com.example.imoviescompose.domain.use_case.get_user.GetUserUseCase
import com.example.imoviescompose.domain.use_case.update_user.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(UserState())
    val state = _state.asStateFlow()

    init {
        getUser()
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            updateUserUseCase(user).collectLatest {  success ->
                if (success) {
                    getUser()
                }
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserUseCase().collectLatest { user ->
                _state.value = UserState(user = user)
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            updateUserUseCase(user).collectLatest {  }
        }
    }
}