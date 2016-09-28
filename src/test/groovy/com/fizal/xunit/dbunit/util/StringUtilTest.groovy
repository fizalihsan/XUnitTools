package com.fizal.xunit.dbunit.util

import spock.lang.Specification

/**
 * Comment here about the class
 * User: Fizal
 * Date: 9/27/2016
 * Time: 8:56 PM
 */
class StringUtilTest extends Specification {

    def "tokenize - empty string"() {
        when:
        def output = StringUtil.tokenize("")

        then:
        output.size() == 1
        output[0] == ''
    }

    def "tokenize - one element"() {
        when:
        def output = StringUtil.tokenize("abc")

        then:
        output.size() == 1
        output[0] == 'abc'
    }

    def "tokenize - two elements"() {
        when:
        def output = StringUtil.tokenize("abc bcd")

        then:
        output.size() == 2
        output[0] == 'abc'
        output[1] == 'bcd'
    }

    def "tokenize - two lines"() {
        when:
        def output = StringUtil.tokenize("abc\nbcd")

        then:
        output.size() == 1
        output[0] == 'abc\nbcd'
    }
}
