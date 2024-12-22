package observer

interface Publisher {
    val subscribers: MutableList<Subscriber>

    fun addSubscriber(subscriber: Subscriber) {
        subscribers.add(subscriber)
    }

    fun removeSubscriber(subscriber: Subscriber) {
        subscribers.remove(subscriber)
    }

    fun notifySubscribers() {
        subscribers.forEach { it.update() }
    }
}