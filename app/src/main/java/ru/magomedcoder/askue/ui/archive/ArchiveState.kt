package ru.magomedcoder.askue.ui.archive

import ru.magomedcoder.askue.domain.model.ElectronicArchive

sealed class ArchiveState {
    class Success(val response: List<ElectronicArchive>) : ArchiveState()
    class Failure(val error: String) : ArchiveState()
    object Empty : ArchiveState()
    object Loading : ArchiveState()
}