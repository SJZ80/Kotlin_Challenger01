val dataClient = Array(3){""}
val articles = Array(5) {""}
val priceArticles = Array(5){ 0 }
val articlesSelected = Array(5) {""}
val articleQuantity = Array(5){ 0 }
val priceLine = Array(5){ 0.0 }

fun populateArticles(){
    articles[0] = "Mouse"
    articles[1] = "Keyboard"
    articles[2] = "Monitor"
    articles[3] = "Printer"
    articles[4] = "notebook"
}

fun populatePriceArticles(){
    priceArticles[0] = 15
    priceArticles[1] = 20
    priceArticles[2] = 30
    priceArticles[3] = 40
    priceArticles[4] = 50
}
fun showArticles(){
    var optionArticle = ""
    articles.forEachIndexed{index, article -> optionArticle+=" ${index.toString()}=$article"}
    println(optionArticle)
}
fun validArticleSelected(indexArt: Int) = indexArt in 0..4

fun requestDataClient(): Array<String>{

    print("Ingrese su Nombre: ")
    dataClient[0] = (readLine()?:"Cliente")
    print("Ingrese su Fecha de Nacimiento dd/mm/yy: ")
    dataClient[1] =(readLine()?:"01/01/1980")
    print("Ingrese su nro de celular: ")
    dataClient[2] = (readLine()?:"0000000")

    return dataClient
}


fun getArticleName(index: Int):String = articles[index]

fun saveArticleSelected(articleID: Int) {
    articlesSelected[articleID] = getArticleName(articleID)
}
fun saveArticleQuantity(articleID: Int,quantity: Int) {
    articleQuantity[articleID] = quantity
}
fun getUnitPrice (indexArt: Int ,quantity: Int)= priceArticles.filterIndexed{index,unitPrice-> index ==indexArt }[0]

fun savePriceLine(indexPriceLine: Int,totalPerLine: Double){
    priceLine[indexPriceLine] = totalPerLine
}

fun getQuantityArticleSold (indexArt: Int)= articleQuantity.filterIndexed{index,articleQuantity-> index == indexArt }[0]

fun getArticlePriceSold (indexArt: Int)= priceLine.filterIndexed{index,priceLine-> index ==indexArt }[0]

val validateEmptyArray = { elemento: String -> elemento.isNotEmpty()}

fun printInvoice(articleSold: Array<String>){
    var totalInvoice = 0.0
    when {
        articleSold.count(validateEmptyArray) > 0 -> {
            println("Venta a: ${dataClient[0]}")
            for ((index, article) in articleSold.withIndex()) {
                if (article != "") {
                    val totalPerLine = getArticlePriceSold(index)
                    println("Articulo Vendido: ${article} Cantidad: ${getQuantityArticleSold(index)} Total por linea: ${totalPerLine}")
                    totalInvoice += totalPerLine
                }
            }
            val totalInvoiceTax = totalInvoice * 1.10
            println("Total Neto: ${totalInvoice} Total Bruto: ${totalInvoiceTax}")
        }
        else -> println("No cargo ninguna Factura")
    }
}


fun createInvoice(){
    var totalInvoice = 0.0
    do {
        showArticles()
        val articleSelected = readLine()?.toInt() ?:0
        println(articleSelected)
        if(validArticleSelected(articleSelected)) saveArticleSelected(articleSelected) else break
        println("Ingrese Cantidad del articulo")
        val quantity = readLine()?.toInt()?:0
        saveArticleQuantity(articleSelected,quantity)
        val linePrice = (quantity.toDouble() * getUnitPrice(articleSelected,quantity).toDouble())
        savePriceLine(articleSelected,linePrice)
        println("Desea agregar mas Articulos a su Compra?")
        val optionSelected: String = readLine()?:"N"

    }while (optionSelected != "N")
}


fun main() {
    populateArticles()
    populatePriceArticles()
    var stop = 0
    var optionMenu = ""
   do {

       print("Cargar factura S/N?")
       optionMenu = readLine()?:"N".toUpperCase()

       when (optionMenu.toUpperCase()){
           "S"->{
               requestDataClient()
               createInvoice()
               printInvoice(articlesSelected)
               optionMenu = "N"
                }
           "N" -> println("Hasta Luego ;)")
           else -> optionMenu = "N"
           }

    }while (optionMenu.toUpperCase() != "N")
}