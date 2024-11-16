package com.jkangangi.en_dictionary.app.util

/**
 * REQUEST_INACTIVE : no ongoing pagination requests.
 *
 * LOADING : Represent the first Loading of the screen.
 *
 * PAGINATING : It is set when the pagination is loading.
 *
 * PAGINATION_EXHAUST : To represent the end of the pagination.
 *
 * EMPTY : This state is to set an Empty screen when the first loading result is empty.
 */
enum class PaginationState {

    REQUEST_INACTIVE,
    LOADING,
    PAGINATING,
    ERROR,
    PAGINATION_EXHAUST,
    EMPTY,
}