package com.tminus1010.tmcommonkotlin.rx

class UnableToBuildAudioRecord(e: UnsupportedOperationException) : RuntimeException(
    """
        |Could not build AudioRecord.
        |Possible cause: RECORD_AUDIO runtime permission was not yet granted. Try requesting the permission.
        |Possible cause: Another AudioRecord was recently closed. Try using retry or retry with delay operators.""".trimMargin(),
    e
)