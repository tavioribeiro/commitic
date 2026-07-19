package org.tavioribeiro.commitic.domain.model.commit

sealed class CommitCleanupMode {
    data object Last : CommitCleanupMode()
    data object LastTwo : CommitCleanupMode()
    data object All : CommitCleanupMode()
}
