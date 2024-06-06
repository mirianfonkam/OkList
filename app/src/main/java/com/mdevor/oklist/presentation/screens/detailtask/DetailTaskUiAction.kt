package com.mdevor.oklist.presentation.screens.detailtask

sealed class DetailTaskUiAction {

    data object ClickBackButton : DetailTaskUiAction()

    data object ClickAddCollaborator : DetailTaskUiAction()

    data object ClickColorPicker : DetailTaskUiAction()

    data object DismissCollabView : DetailTaskUiAction()

    data class UpdateCollabPermission(
        val isChecked: Boolean
    ) : DetailTaskUiAction()

    data class UpdateCollabEmail(
        val email: String
    ) : DetailTaskUiAction()

    data object ConfirmCollabConfiguration : DetailTaskUiAction()

    data class UpdateTitle(val title: String) : DetailTaskUiAction()

    data object AddSubtask : DetailTaskUiAction()

    data object DeleteTask : DetailTaskUiAction()

    data class UpdateSubtaskDescription(
        val subtaskUuid: String,
        val description: String
    ) : DetailTaskUiAction()

    data class UpdateSubtaskCompletion(
        val subtaskUuid: String,
        val isChecked: Boolean
    ) : DetailTaskUiAction()

    data class UpdateTaskColorIndicator(val color: Long) : DetailTaskUiAction()

    data object DismissColorView : DetailTaskUiAction()

    data class DeleteSubtask(val subtaskUuid: String) : DetailTaskUiAction()
}