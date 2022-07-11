module.exports = {
    parserOptions: {
        "parser": "babel-eslint",
        "sourceType": "module",
        "ecmaVersion": 6
    },
    env: {
        browser: true,
        node: true,
        es6: true,
    },
    extends: ["eslint:recommended", "plugin:vue/essential"],
    rules: {
        // "no-console":  process.env.NODE_ENV === 'production' ? 'error' : 'off',
        "no-console":  'off',
        "vue/multi-word-component-names": 0,
        "no-undef": 1,
        "no-unused-vars": 1
    }
}