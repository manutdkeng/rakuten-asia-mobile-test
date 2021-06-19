package com.zack.android.test.rakuten.util

import com.google.common.truth.Fact
import com.google.common.truth.FailureMetadata
import com.google.common.truth.Subject
import com.google.common.truth.Truth.assertAbout
import com.zack.android.test.rakuten.utils.Result

class ResultSubject<R : Any>(metadata: FailureMetadata, private val actual: Result<R>) :
    Subject(metadata, actual) {

    fun isError() {
        if (actual !is Result.Error) {
            failWithActual(Fact.simpleFact("expected to be an Error"))
        }
    }

    fun isError(messageId: Int) {
        if (actual !is Result.Error) {
            failWithActual(Fact.simpleFact("expected to be an Error"))
        }

        val target = actual as Result.Error
        if (target.messageId != messageId) {
            failWithActual(Fact.simpleFact("expected the message ID to be '$messageId' but is '${target.messageId}' instead"))
        }
    }

    fun isSuccess() {
        if (actual !is Result.Success) {
            failWithActual(Fact.simpleFact("expected to be a Success"))
        }
    }

    fun <T : Any> isSuccess(expected: T) {
        if (actual !is Result.Success) {
            failWithActual(Fact.simpleFact("expected to be a Success"))
        }

        val target = actual as Result.Success
        if (target.data::class != expected::class) {
            failWithActual(Fact.simpleFact("expected the data class to be ${expected::class.java.canonicalName} but is ${target.data::class.java.canonicalName} instead"))
        }

        if (target.data != expected) {
            failWithActual(Fact.simpleFact("expected the data to be $expected but is ${target.data} instead"))
        }
    }
}

fun result(): Subject.Factory<ResultSubject<Any>, Result<Any>> {
    return Subject.Factory { metadata, actual ->
        ResultSubject(metadata, actual)
    }
}

fun assertResult(result: Result<Any>): ResultSubject<Any> {
    return assertAbout(result()).that(result)
}