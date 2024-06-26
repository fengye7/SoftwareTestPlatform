const query = /* GraphQL */ `
  # Copyright (c) 2021 GraphQL Contributors
  # All rights reserved.
  #
  # This source code is licensed under the BSD-style license found in the
  # LICENSE file in the root directory of this source tree. An additional grant
  # of patent rights can be found in the PATENTS file in the same directory.

  query queryName($foo: TestInput, $site: TestEnum = RED) {
    testAlias: hasArgs(string: "testString")
    ... on Test {
      hasArgs(
        listEnum: [RED, GREEN, BLUE]
        int: 1
        listFloat: [1.23, 1.3e-1, -1.35384e+3]
        boolean: true
        id: 123
        object: $foo
        enum: $site
      )
    }
    test @include(if: true) {
      union {
        __typename
      }
    }
    ...frag
    ... @skip(if: false) {
      id
    }
    ... {
      id
    }
  }

  mutation mutationName {
    setString(value: "newString")
  }

  subscription subscriptionName {
    subscribeToTest(id: "anId") {
      ... on Test {
        id
      }
    }
  }

  fragment frag on Test {
    test @include(if: true) {
      union {
        __typename
      }
    }
  }
`;

export default query;