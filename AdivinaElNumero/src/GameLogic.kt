import kotlin.random.Random
import java.io.File


//Función que se encarga de generar un número aleatorio de 4 cifras comprendidas entre 1 y 6 , sin repetirse.
fun generateRandomNumber(): String {

    val avaliableNumbers = mutableListOf(1, 2, 3, 4, 5, 6)
    val secretNumber = mutableListOf<Int>()


    while (secretNumber.size < 4) {
        val index = Random.nextInt(avaliableNumbers.size)
        secretNumber.add(avaliableNumbers.removeAt(index))
    }

    return secretNumber.joinToString("")
}

//Función que gestiona la entrada del Usuario durante la partida
fun userEntry(): String {
    while (true) {

        print("Introduce un número de 4 cifras entre 1 y 6: ")
        val entry = readln()

        if (entry.length == 4 && entry.all { it in '1'..'6' } && entry.toSet().size == 4) {
            return entry
        } else {
            println("Entrada inválida. Asegúrate de que el número tenga 4 cifras únicas entre 1 y 6.")
        }
    }
}


//Función que comprueba la entrada del usuario y la compara con el número secreto.
//Si la cifra no esta en el número secreto se vera rojo, si esta en el número pero en la posción
//incorrecta se vera en amarrilo y si esta en el numero y en la posicion correcta sera verde.
fun checkNumbers(secretNumber:String, attemp:String):Pair<Int,Int>  {

    var correctNumbersAndPosition = 0
    var correctNumber = 0
    val secretList = secretNumber.toList()
    val attempList = attemp.toList()


    val checked = mutableListOf<Boolean>(false,false,false,false)
    val colors = mutableListOf<String>("","","","")



    // Contar los aciertos
    for (i in attempList.indices) {
        if (attempList[i] == secretList[i]) {
            correctNumbersAndPosition++
            colors[i] = "\u001B[32m"
            checked[i]=true

        }
    }

    // Contar las coincidencias
    for (i in attempList.indices) {
        if (attempList[i] != secretList[i] && secretList.contains(attempList[i])) {
            correctNumber++
            colors[i] = "\u001B[33m"
            checked[i]=true
        }
    }


    for(i in attempList.indices){
        if(!checked[i]){
            colors[i] = "\u001B[31m"
        }
    }

    //Imprime el número del User con los colores adecuados
    for(i in attempList.indices){
        print("${colors[i]}${attempList[i]}\u001B[0m")
    }

    println()
    return Pair(correctNumbersAndPosition, correctNumber)


}

//Función que incia el juego
fun play() {
    val secretNumber = generateRandomNumber()
    var attemps = 0
    val maxAattepms = 10

    println("Bienvenido al juego de Adivina el Número!")
    println("El número secreto tiene 4 cifras entre 1 y 6 (sin repetirse).")

    while (attemps < maxAattepms) {
        val attemp = userEntry()
        val (correctNumberAndPosition, correctNumber) = checkNumbers(secretNumber, attemp)

        attemps++

        println("Intento #$attemps: $attemp")


        if (correctNumberAndPosition == 4) {
            println("¡Felicidades! Adivinaste el número secreto: $secretNumber")
            println("Seleccione una opción:")
            println("1. Jugar")
            println("2. Ver partida anterior")
            println("3. Salir")
            break
        }

        if (attemps == maxAattepms) {
            println("¡Lo siento! Has agotado tus intentos. El número secreto era: $secretNumber")
            println("Seleccione una opción:")
            println("1. Jugar")
            println("2. Ver partida anterior")
            println("3. Salir")
            break
        }
    }

    // Guardar el último intento en un archivo
    saveLastGame(secretNumber, attemps)
}


//Función encargada de guardar los datos de la última partida
fun saveLastGame(secretNumber: String, attemps: Int) {
    val file = File("ultima_jugada.txt")
    file.writeText("Número secreto: $secretNumber\nIntentos: $attemps\n")
}

//Función encargada de cargar los datos de la última partida
fun loadLastGame(){
    val file = File("ultima_jugada.txt")

    if(file.exists()){
        val game = file.readText()

        println(game)
        println()
        println("Seleccione una opción:")
        println("1. Jugar")
        println("2. Ver partida anterior")
        println("3. Salir")



    }else{
        println("No hay partida anterior guardada.")

        println()
        println("Seleccione una opción:")
        println("1. Jugar")
        println("2. Ver partida anterior")
        println("3. Salir")
    }
}
