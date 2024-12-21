package observer

interface Publisher {
    val subscribers: MutableList<Subscriber>

    fun notifyAll() {
        subscribers.forEach { it.update() }
    }

    fun addSubscriber(subscriber: Subscriber)

    fun removeSubscriber(subscriber: Subscriber)
}