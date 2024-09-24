package org.example.repository

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import kotlin.io.path.notExists

// Construir una función reciba el fichero de cotizaciones y devuelva un diccionario con los datos del fichero por columnas.
//
// Construir una función que reciba el diccionario devuelto por la función anterior
// y cree un fichero en formato csv con el mínimo, el máximo y la media de dada columna.

class CompanyRepository(private val fileDir:Path) {

    fun createMap() :Map<String, List<String>> {
        val comapnyMap :MutableMap<String, List<String>> = mutableMapOf()
        val br = Files.newBufferedReader(fileDir)
        br.use {
            it.forEachLine { line ->
                val lineCompany = line.split(";")
                comapnyMap[lineCompany[0]] = listOf(lineCompany[1], lineCompany[2],lineCompany[3],lineCompany[4],lineCompany[5])
            }
        }
        return comapnyMap
    }


    fun createFile(companyMap:Map<String, List<String>>, newFileDir:Path) {

        if (newFileDir.notExists()) {
            Files.createFile(newFileDir)
        }

        val bw = Files.newBufferedWriter(newFileDir, StandardOpenOption.APPEND)
        bw.use { flujo ->
            var media:Float
            companyMap.forEach{ map ->
                if(map.value[1].replace(",", ".").toFloatOrNull() != null) {
                    val max = map.value[1].replace(",", ".").toFloat()
                    val min = map.value[2].replace(",", ".").toFloat()
                    media = (max + min) /2
                    flujo.write("${map.key};${map.value[1]};${map.value[2]};$media")
                } else flujo.write("${map.key};${map.value[1]};${map.value[2]};Media")

                flujo.newLine()
            }
        }
    }

}