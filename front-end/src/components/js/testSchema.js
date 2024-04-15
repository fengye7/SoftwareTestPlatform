"use strict";
/* istanbul ignore file */
/**
 *  Copyright (c) 2021 GraphQL Contributors
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree. An additional grant
 *  of patent rights can be found in the PATENTS file in the same directory.
 */
Object.defineProperty(exports, "__esModule", { value: true });
exports.TestSchema = exports.TestType = exports.TestUnion = exports.UnionSecond = exports.UnionFirst = exports.TestInputObject = exports.TestEnum = void 0;
var graphql_1 = require("graphql");
// Test Schema
exports.TestEnum = new graphql_1.GraphQLEnumType({
    name: 'TestEnum',
    values: {
        RED: {},
        GREEN: {},
        BLUE: {},
    },
});
exports.TestInputObject = new graphql_1.GraphQLInputObjectType({
    name: 'TestInput',
    fields: function () { return ({
        string: { type: graphql_1.GraphQLString },
        int: { type: graphql_1.GraphQLInt },
        float: { type: graphql_1.GraphQLFloat },
        boolean: { type: graphql_1.GraphQLBoolean },
        id: { type: graphql_1.GraphQLID },
        enum: { type: exports.TestEnum },
        object: { type: exports.TestInputObject },
        // List
        listString: { type: new graphql_1.GraphQLList(graphql_1.GraphQLString) },
        listInt: { type: new graphql_1.GraphQLList(graphql_1.GraphQLInt) },
        listFloat: { type: new graphql_1.GraphQLList(graphql_1.GraphQLFloat) },
        listBoolean: { type: new graphql_1.GraphQLList(graphql_1.GraphQLBoolean) },
        listID: { type: new graphql_1.GraphQLList(graphql_1.GraphQLID) },
        listEnum: { type: new graphql_1.GraphQLList(exports.TestEnum) },
        listObject: { type: new graphql_1.GraphQLList(exports.TestInputObject) },
    }); },
});
var TestInterface = new graphql_1.GraphQLInterfaceType({
    name: 'TestInterface',
    resolveType: function () { return exports.UnionFirst; },
    fields: {
        scalar: {
            type: graphql_1.GraphQLString,
            resolve: function () { return ({}); },
        },
    },
});
var AnotherTestInterface = new graphql_1.GraphQLInterfaceType({
    name: 'AnotherTestInterface',
    resolveType: function () { return exports.UnionFirst; },
    fields: {
        example: {
            type: graphql_1.GraphQLString,
            resolve: function () { return ({}); },
        },
    },
});
exports.UnionFirst = new graphql_1.GraphQLObjectType({
    name: 'First',
    interfaces: [TestInterface, AnotherTestInterface],
    fields: function () { return ({
        scalar: {
            type: graphql_1.GraphQLString,
            resolve: function () { return ({}); },
        },
        first: {
            type: exports.TestType,
            resolve: function () { return ({}); },
        },
        example: {
            type: graphql_1.GraphQLString,
            resolve: function () { return ({}); },
        },
    }); },
});
exports.UnionSecond = new graphql_1.GraphQLObjectType({
    name: 'Second',
    fields: function () { return ({
        second: {
            type: exports.TestType,
            resolve: function () { return ({}); },
        },
    }); },
});
exports.TestUnion = new graphql_1.GraphQLUnionType({
    name: 'TestUnion',
    types: [exports.UnionFirst, exports.UnionSecond],
    resolveType: function () {
        return exports.UnionFirst;
    },
});
exports.TestType = new graphql_1.GraphQLObjectType({
    name: 'Test',
    fields: function () { return ({
        test: {
            type: exports.TestType,
            resolve: function () { return ({}); },
        },
        deprecatedTest: {
            type: exports.TestType,
            deprecationReason: 'Use test instead.',
            resolve: function () { return ({}); },
        },
        union: {
            type: exports.TestUnion,
            resolve: function () { return ({}); },
        },
        first: {
            type: exports.UnionFirst,
            resolve: function () { return ({}); },
        },
        id: {
            type: graphql_1.GraphQLInt,
            resolve: function () { return ({}); },
        },
        isTest: {
            type: graphql_1.GraphQLBoolean,
            resolve: function () {
                return true;
            },
        },
        hasArgs: {
            type: graphql_1.GraphQLString,
            resolve: function (_value, args) {
                return JSON.stringify(args);
            },
            args: {
                string: { type: graphql_1.GraphQLString },
                int: { type: graphql_1.GraphQLInt },
                float: { type: graphql_1.GraphQLFloat },
                boolean: { type: graphql_1.GraphQLBoolean },
                id: { type: graphql_1.GraphQLID },
                enum: { type: exports.TestEnum },
                object: { type: exports.TestInputObject },
                // List
                listString: { type: new graphql_1.GraphQLList(graphql_1.GraphQLString) },
                listInt: { type: new graphql_1.GraphQLList(graphql_1.GraphQLInt) },
                listFloat: { type: new graphql_1.GraphQLList(graphql_1.GraphQLFloat) },
                listBoolean: { type: new graphql_1.GraphQLList(graphql_1.GraphQLBoolean) },
                listID: { type: new graphql_1.GraphQLList(graphql_1.GraphQLID) },
                listEnum: { type: new graphql_1.GraphQLList(exports.TestEnum) },
                listObject: { type: new graphql_1.GraphQLList(exports.TestInputObject) },
            },
        },
    }); },
});
var TestMutationType = new graphql_1.GraphQLObjectType({
    name: 'MutationType',
    description: 'This is a simple mutation type',
    fields: {
        setString: {
            type: graphql_1.GraphQLString,
            description: 'Set the string field',
            args: {
                value: { type: graphql_1.GraphQLString },
            },
        },
    },
});
var TestSubscriptionType = new graphql_1.GraphQLObjectType({
    name: 'SubscriptionType',
    description: 'This is a simple subscription type',
    fields: {
        subscribeToTest: {
            type: exports.TestType,
            description: 'Subscribe to the test type',
            args: {
                id: { type: graphql_1.GraphQLString },
            },
        },
    },
});
var OnArgDirective = new graphql_1.GraphQLDirective({
    name: 'onArg',
    locations: [graphql_1.DirectiveLocation.ARGUMENT_DEFINITION],
});
var OnAllDefsDirective = new graphql_1.GraphQLDirective({
    name: 'onAllDefs',
    locations: [
        graphql_1.DirectiveLocation.SCHEMA,
        graphql_1.DirectiveLocation.SCALAR,
        graphql_1.DirectiveLocation.OBJECT,
        graphql_1.DirectiveLocation.FIELD_DEFINITION,
        graphql_1.DirectiveLocation.INTERFACE,
        graphql_1.DirectiveLocation.UNION,
        graphql_1.DirectiveLocation.ENUM,
        graphql_1.DirectiveLocation.ENUM_VALUE,
        graphql_1.DirectiveLocation.INPUT_OBJECT,
        graphql_1.DirectiveLocation.ARGUMENT_DEFINITION,
        graphql_1.DirectiveLocation.INPUT_FIELD_DEFINITION,
    ],
});
exports.TestSchema = new graphql_1.GraphQLSchema({
    query: exports.TestType,
    mutation: TestMutationType,
    subscription: TestSubscriptionType,
    directives: [
        graphql_1.GraphQLIncludeDirective,
        graphql_1.GraphQLSkipDirective,
        graphql_1.GraphQLDeprecatedDirective,
        OnArgDirective,
        OnAllDefsDirective,
    ],
});
