package com.fizal.xunit.dbunit.util

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Comment here about the class
 * User: Fizal
 * Date: 9/27/2016
 * Time: 8:56 PM
 */
@Unroll
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

    def "splitCommands"() {
        expect:
        StringUtil.splitCommands(input) == output

        where:
        input   | output
        ""      | []
        "A"     | ["A"]
        "A;B"   | ["A", "B"]
        "A ; B" | ["A", "B"]
    }

    def "getQuotePositions"() {
        expect:
        StringUtil.getQuotePositions(input) == output

        where:
        input          | output
        ""             | []
        "\"\""         | [0, 1]
        "\"A\""        | [0, 2]
        "\"A\", \"B\"" | [0, 2, 5, 7]
    }

}
