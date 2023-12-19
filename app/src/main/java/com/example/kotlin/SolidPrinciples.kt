package com.example.kotlin


/**
 * S (Single Responsibility)
 * - Every class should have only one responsibility
 */
data class User(val name: String)
//VIOLATION
// In this example the System class is trying to handle many different //situations at the same time.
class SystemManager {
    fun addUser(user: User) { }
    fun deleteUser(user: User) { }
    fun sendNotification(notification:String) { }
    fun sendEmail(user: User, email: String) { }
}
//CORRECT
//we divided our System class into specific parts
class MailManager() {
    fun sendEmail(user: User, email: String) {}
}
class NotificationManager() {
    fun sendNotification(notification: String) {}
}
class UserManager {
    fun addUser(user: User) {}
    fun deleteUser(user: User) {}
}

/**
 * O (Open-Closed)
 * - Add new things to a class without changing its original code.
 * - Open: This means that we can add new features to our classes. When there is a new requirement, we should be able to add new features to our class easily.
 * - Close: This means that the base features of the class should not be changed.
 */

data class Shappe(val type: String, val width: Double, val height: Double)
//VIOLATION
//when we try to add something new to our class,
//we have to rewrite our existing code
fun calculateArea(shape: Shappe): Double {
    if (shape.type == "rectangle") {
        return shape.width * shape.height
    } else if (shape.type == "circle") {
        return Math.PI * shape.width * shape.width
    }
    return 0.0
}
//CORRECT
// instead of changing the class itself,
// we wrote new classes using our existing class
// and implemented our functions under new classes.
interface Shape {
    fun area(): Double
}
class Rectangle(val width: Double, val height: Double) : Shape {
    override fun area() = width * height
}
class Circle(val radius: Double) : Shape {
    override fun area() = Math.PI * radius * radius
}
fun calculateArea(shape: Shape) = shape.area()

/**
 * L (Liskov Substitution)
 * - All the methods and properties in the main class should also work for all the sub-classes without needing to change anything.
 */
//VIOLATION
// As we saw in this example, the method we wrote in our main class should work properly in its subclasses according to the Liskov principle,
// but when our subclass inherited from our superclass, our fly method did not work as expected.
open class Biird {
    open fun fly() {}
}
class Penguin : Biird() {
    override fun fly() {
        print("Penguins can't fly!")
    }
}
//CORRECT
// As you can see in this example, all the things we write in the superclass will be valid in the subclasses,
// because we have implemented the method that is not valid for subclasses by creating an interface and implementing it where we need it.
open class Birrd {
    // common bird methods and properties
}
interface IFlyingBird {
    fun fly(): Boolean
}
class Penguiin : Birrd() {
    // methods and properties specific to penguins
}
class Eagle : Birrd(), IFlyingBird {
    override fun fly(): Boolean {
        return true
    }
}

/**
 * I (Interface Segregation)
 * - This principle states that once an interface becomes too fat, it needs to be split into smaller interfaces so that client of the interface will only know about the methods that pertain to them.
 */
//VIOLATION
// not every animal have swim or fly capability
interface Aniimal {
    fun swim()
    fun fly()
}
//CORRECT
interface CanSwim {
    fun swim()
}
interface CanFly {
    fun fly()
}

/**
 * D (Dependency Inversion)
 * - High-level modules should not depend on low-level modules, but both should depend on abstractions
 */
//VIOLATION
class PaypalPaymentProcessor {
    fun processPayment(amount: Double): Boolean {return true}
}
class PaymentService {
    private val paymentProcessorPaypal = PaypalPaymentProcessor()
    fun processPaymentWithPaypal(amount: Double): Boolean {
        return paymentProcessorPaypal.processPayment(amount)
    }
}
//CORRECT
interface PaymentProcessor {
    fun processPayment(amount: Double): Boolean
}
//PaypalPaymentProcessor implements PaymentProcessor and can be provided from outside
class PaymentSerrvice(private val paymentProcessor: PaymentProcessor) {
    fun processPayment(amount: Double): Boolean {
        return paymentProcessor.processPayment(amount)
    }
}

fun main() {

}