fun main() {
    println("Seleccione una opción:")
    println("1. Jugar")
    println("2. Ver partida anterior")
    println("3. Salir")

//Según la entrada del Usuario se elige lo que se quiere hacer
    while(true) {
        val entry= readln()
        if(entry == "1"){
            play()
        }else if(entry == "2"){
            loadLastGame()
        }else if(entry == "3"){
            return
        }else{
            print("Introduce un número válido")

        }
    }
}