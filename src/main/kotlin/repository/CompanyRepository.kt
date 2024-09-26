package org.example.repository

import java.io.BufferedReader
import java.io.BufferedWriter
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
                val lineCompany = line.replace(",", ".").split(";")
                if (lineCompany[1].toFloatOrNull() != null) comapnyMap[lineCompany[0]] = listOf(lineCompany[1], lineCompany[2],lineCompany[3],lineCompany[4],lineCompany[5])
            }
        }
        return comapnyMap
    }


    fun createFile(companyMap:Map<String, List<String>>, newFileDir:Path) {

        val bw = Files.newBufferedWriter(newFileDir, StandardOpenOption.APPEND)

        if (newFileDir.notExists()) {
            Files.createFile(newFileDir)
        }
        val br = Files.newBufferedReader(newFileDir)

        bw.use { flujo ->
            checkTittle(br, flujo)
            var media:Float
            companyMap.forEach{ map ->
                val max = map.value[1].toFloat()
                val min = map.value[2].toFloat()
                media = (max + min) /2
                flujo.write("${map.key};${map.value[1]};${map.value[2]};$media")
                flujo.newLine()
            }
        }
    }

    private fun checkTittle(br:BufferedReader, flujo:BufferedWriter) {
        br.use {
            if (it.lines().toList().isEmpty()) {
                flujo.write("Nombre;Máximo;Mínimo;Media")
                flujo.newLine()
            }
        }
    }

}