package oop
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
//TODO
// sacar los return de los add
// Falta implementar el almacenamiento de todas las Facturas Cargadas

//https://devexperto.com/data-classes-kotlin/


data class Customer(val name: String, val birthDate: String,val cellPhone: String)

data class Article(val name: String,val description: String,val unitPrice: Double)

data class InvoiceLine(val article: Article, val quantiy: Int)

class Invoice(val customer: Customer, private val lines:ArrayList<InvoiceLine> = arrayListOf<InvoiceLine>()){
    var nroFactura: Int = 0

    init {
        nroFactura = Invoice.createNroFactura()
    }
    //Autonumber Invoice
    private companion object{
        var _nro_factura: Int = 0
        private val TAX = 1.10;
        private fun createNroFactura (): Int{
            return  ++_nro_factura
        }
    }
    fun addLine(line: InvoiceLine){
        lines.add(line)
    }

    fun printInvoice(){
        var totalInvoice = 0.0
        println("Nro Factura:${nroFactura}\t Cliente: ${customer.name}\t Fecha: ${LocalDate.now()}")
        println("Articulo\tCantidad\tPrecio Unit")
        for (line in lines){
            println("${line.article.name}\t\t${line.quantiy}\t\t\t${line.article.unitPrice}\t${(line.quantiy * line.article.unitPrice).toDouble()}")
            totalInvoice += (line.quantiy * line.article.unitPrice).toDouble()
        }
        println("Total Neto: $${totalInvoice} Total Bruto: $${totalInvoice * Invoice.TAX}")
    }

}
enum class OptionsMenu(val valorOption: Int = 0) {
    INICIO,
    ARTICULOS(1),
    CLIENTES(2),
    FACTURA(3),
    EXIT(4);
}
object MainMenu{

        infix fun initProgram(_optionMenu: Int){
        var optionMenu = _optionMenu
        do {
            println("********************************* Arroba Systems ****************************************")
            println("*****************************************************************************************")
            println("Seleccione una Opcion 1.-Cargar Articulos 2.- Cargar Clientes 3.- Generar Factura 4.-Exit")
            println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
            optionMenu = readLine()?.toInt() ?:OptionsMenu.EXIT.valorOption

            if (validateOptionValue(optionMenu)) executeOptionMenu(optionMenu) else optionMenu = 4

        }while (optionMenu != 4)
    }

    private fun validateOptionValue(selectedOption: Int) = selectedOption in OptionsMenu.ARTICULOS.valorOption..OptionsMenu.FACTURA.valorOption

    private fun executeOptionMenu(selectedOption: Int): Int{

        when (selectedOption) {
            OptionsMenu.ARTICULOS.valorOption -> {
                addArticle()
            }
            OptionsMenu.CLIENTES.valorOption -> {
                addCustomer()
            }
            OptionsMenu.FACTURA.valorOption -> {
                addInvoice()
            }
        }
        return 1
    }

    private fun addArticle(){
        var run: Boolean = true
        do {
            println("Ingrese Articulo")
            val nameArticle = readLine()?:" "
            println("Ingrese Descripcion")
            val descriptionArticle = readLine()?:" "
            println("Ingrese Precio Unitario")
            val unitPrice = readLine()?.toDouble()?:0.0
            ArticleList addArticle Article(nameArticle,descriptionArticle,unitPrice)
            println(ArticleList.showArticle())
            println("Desea seguir cargando articulos?S/N")
            val loadArticle = readLine()?:"N"
            run = loadArticle.toUpperCase() == "S"
        }while (run)
        return
    }

    private fun addCustomer(){
        var run: Boolean = true
        do {
            println("Ingrese Cliente")
            val nameCustomer = readLine()?:" "
            println("Ingrese Fecha de Nacimiento")
            val birthDate = readLine()?:" "
            println("Ingrese Celular")
            val cellPhone = readLine()?:""
            CustomerList addCustomer Customer(nameCustomer,birthDate,cellPhone)
            println("Desea seguir cargando Clientes? S/N")
            val loadCustomer = readLine()?:"N"
            run = loadCustomer.toUpperCase() == "S"
        }while (run)
        return
    }

    private fun addInvoice(){

        var run: Boolean = true

        println("***Seleccione Cliente***")
        println(CustomerList.showCustomers())
        val numberCustomer = readLine()?.toInt()?:0
        val customerSelected:Customer =
                if (CustomerList isValid numberCustomer) CustomerList returnCustomer numberCustomer else return
        val invoice: Invoice = Invoice(customerSelected)
        do {
            println("***Seleccione Articulo***")
            println(ArticleList.showArticle())
            val numberArticle = readLine()?.toInt()?:0
            val articleSelected:Article =
                    if (ArticleList isValid numberArticle) ArticleList returnArticle numberArticle else return
            println("***Ingrese Cantidad***")
            val quantiy: Int = readLine()?.toInt()?:0
            invoice.addLine(InvoiceLine(articleSelected, quantiy))
            println("***Desea seguir Comprando?S/N***")
            val loadLine = readLine()?:"N"
            run = loadLine.toUpperCase() == "S"
        }while (run)
        invoice.printInvoice()
        //Esto es para persistir las facturas cargadas, no implementado
        //InvoiceList addInvoice invoice
        return
    }
}
//Stock Article
object ArticleList{

    private val stockArticle: ArrayList<Article> = ArrayList<Article>()

    infix fun addArticle(article: Article){
        stockArticle.add(article)
    }
    fun showArticle(): String{
        var listArticles: String = ""
        var id: Int = 0
        stockArticle.forEach { (name) ->listArticles+="${id++}-$name\n" }
        return listArticles

    }
    infix fun isValid(id: Int) = id in 0 until stockArticle.size
    infix fun returnArticle(id: Int):Article= stockArticle.filterIndexed{ index, article -> index == id }[0]
}
//Customer Master
object CustomerList{

    private val customerList: ArrayList<Customer> = ArrayList<Customer>()

    infix fun addCustomer(customer: Customer){
        customerList.add(customer)

    }
    fun showCustomers(): String{
        var listCustomer: String = ""
        var id: Int = 0
        customerList.forEach { (name) ->listCustomer+="${id++}-$name\n" }
        return listCustomer
    }

    infix fun isValid(id: Int) = id in 0 until customerList.size

    infix fun returnCustomer(id: Int):Customer= customerList.filterIndexed{ index,customer -> index == id }[0]

}
// Esto no esta implementado es para persistir en memoria todas
// las Facturas Cargadas
/*object InvoiceList{

    private val invoiceList: ArrayList<Invoice> = ArrayList<Invoice>()

    infix fun addInvoice(invoice: Invoice){
        invoiceList.add(invoice)
    }
    //infix fun isValid(id: Int) = id in 0 until customerList.size
    infix fun returnInvoice(id: Int):Invoice= invoiceList.filterIndexed{ index,invoice -> index == id }[0]
}*/
fun main() {
     MainMenu initProgram 0
}