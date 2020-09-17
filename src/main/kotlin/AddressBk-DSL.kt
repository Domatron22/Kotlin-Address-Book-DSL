/*
 * Author: Dominic Triano
 * Date: 3/28/2019
 * Language: Kotlin
 * Description:
 * Creation of a DSL for class
 *
 */




@DslMarker
annotation class AddressBkDSL

data class AddressBk(var contacts: List<Contact>)

data class Contact(var firstName: String, var lastName: String, var mobile: String, var addresses: List<Address>)

data class Address(var street: String, var city: String, var state: String, var zipCode: String)

@AddressBkDSL
class ContactBuilder {
    var firstName: String = ""
    var lastName: String = ""
    var mobile: String = ""
    var addresses =  mutableListOf<Address>()

    fun build() = Contact(firstName, lastName, mobile, addresses)
}

@AddressBkDSL
class AddressBuilder {
    var street: String = ""
    var city: String = ""
    var state: String = ""
    var zipCode: String = ""

    fun build() = Address(street, city, state, zipCode)
}

@AddressBkDSL
class AddressBkBuilder() {
    var contacts =  mutableListOf<Contact>()

    fun build() = AddressBk(contacts)
}

fun addressBk(init: AddressBkBuilder.() -> Unit): AddressBk {
    val builder = AddressBkBuilder()
    builder.init()
    return builder.build()
}

fun AddressBkBuilder.contacts(init: ContactBuilder.() -> Unit) {
        val builder = ContactBuilder()
        builder.init()
        val contact = builder.build()
        contacts.add(contact)
}

fun ContactBuilder.addresses(init: AddressBuilder.() -> Unit) {
    val builder = AddressBuilder()
    builder.init()
    val address = builder.build()
    addresses.add(address)
}

fun main(args: Array<String>) {
    val a = addressBk(){
        contacts {
            firstName = "John"
            lastName =  "Doe"
            mobile = "123-123-1234"
            addresses {
                street = "11 Abc Ave"
                city = "Utica"
                state = "NY"
                zipCode = "13501"
            }
        }

        contacts {
            firstName = "Jane"
            lastName =  "Doe"
            mobile = "123-123-1235"
            addresses {
                street = "12 Def Dr"
                city = "Utica"
                state = "NY"
                zipCode = "13502"
            }
        }
    }
    println("Your address book:")
    a.contacts.forEach {
        println("\n${it.firstName} ${it.lastName}" + "\n${it.mobile}")
        it.addresses.forEach{
            println("\t${it.street}" +
                    "\n\t${it.city}" +
                    "\n\t${it.state}" +
                    "\n\t${it.zipCode}")
            }
    }

}
