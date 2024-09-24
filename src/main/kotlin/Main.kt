package org.example

import org.example.repository.CompanyRepository
import kotlin.io.path.Path

fun main() {
    val fileDir = Path("src/main/resources/cotizacion.csv")
    val newFileDir = Path("src/main/resources/mediaCotizacion.csv")

    val companyRepository = CompanyRepository(fileDir)

    val companyMap = companyRepository.createMap()

    companyRepository.createFile(companyMap, newFileDir)
}