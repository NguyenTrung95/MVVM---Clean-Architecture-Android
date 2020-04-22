package com.nencer.thesieure.base.viewmodel

sealed class StateActionEvent

object LoadState : StateActionEvent()

object SuccessState : StateActionEvent()

class ErrorState(val message: String?) : StateActionEvent()