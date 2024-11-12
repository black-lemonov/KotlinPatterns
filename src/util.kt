
val NAME_REGEX = Regex("^[А-Яа-яA-Za-z]+$")
val TG_REGEX = Regex("^@?\\w+$")
val EMAIL_REGEX = Regex("^\\w+@\\w+\\.\\w+$")
val GIT_REGEX = Regex("^(git@|https://|http://|ssh://)([a-zA-Z0-9._-]+)(:[0-9]+)?(/.+|.[^.]+)\\.git\$")
val PHONE_REGEX = Regex("^\\+?\\d{11}$")

fun checkName(name: String?): Boolean {
    if (name == null ) {
        return true
    }
    return NAME_REGEX.matches(name)
}

fun checkGit(git: String) = GIT_REGEX.matches(git)

fun checkTg(tg: String?): Boolean {
    if (tg == null) {
        return true
    }
    return TG_REGEX.matches(tg)
}

fun checkEmail(email: String?): Boolean {
    if (email == null) {
        return true
    }
    return EMAIL_REGEX.matches(email)
}

fun checkPhone(phone: String?): Boolean {
    if (phone == null) {
        return true
    }
    return PHONE_REGEX.matches(phone)
}

fun convToType(key: String, valStr: String, haystack: Iterable<String>): Any? {
    val typedVal = when (key) {
        "id" -> valStr.toUInt()
        in haystack -> valStr.ifEmpty { null }
        else -> throw IllegalArgumentException("Поле $key не найдено в haystack!")
    }
    return typedVal
}


