package com.denistiago.urlshortener.repository

import spock.lang.Specification
import spock.lang.Unroll

class Base62Test extends Specification {

    @Unroll
    def 'given #id expect #expected'() {
        when:
        def encoded = Base62.encode(id)

        then:
        encoded == expected

        where:
        id | expected
        1 | 'b'
        2 | 'f'
        3 | 'N'
        4 | '7'
        5 | 'S'
        6 | 'D'
        7 | '9'
        8 | 'o'
        9 | '6'
        10 | 'v'
        11 | 'V'
        12 | 'M'
        13 | '0'
        14 | 'G'
        15 | 'i'
        16 | 'A'
        17 | 'U'
        18 | 'q'
        19 | 'J'
        20 | '2'
        21 | 'B'
        22 | 'j'
        23 | 'd'
        24 | 'u'
        25 | 't'
        26 | 'g'
        27 | 'l'
        28 | 'H'
        29 | 'T'
        30 | '4'
        31 | '8'
        32 | 'x'
        33 | 'X'
        34 | '1'
        35 | 'I'
        36 | 'Y'
        37 | 'e'
        38 | 'P'
        39 | 'E'
        40 | 'z'
        41 | 'c'
        42 | 'L'
        43 | 'O'
        44 | 'W'
        45 | '5'
        46 | 'w'
        47 | '3'
        48 | 'm'
        49 | 'Z'
        50 | 'F'
        51 | 'r'
        52 | 'k'
        53 | 'C'
        54 | 'K'
        55 | 'Q'
        56 | 'R'
        57 | 'a'
        58 | 's'
        59 | 'h'
        60 | 'y'
        61 | 'n'

    }

}
