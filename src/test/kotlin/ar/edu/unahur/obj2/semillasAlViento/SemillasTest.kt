package ar.edu.unahur.obj2.semillasAlViento

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class SemillasTest : DescribeSpec({
    describe("Plantas") {
        describe("Mentas") {
            describe("Menta alta 0.9") {
                val menta = Menta(2018, 0.9F)
                it("No es fuerte") {
                    menta.esFuerte().shouldBeFalse()
                }
                it("Da Semillas") {
                    menta.daSemillas().shouldBeTrue()
                }
            }
            describe("Menta baja 0.3") {
                val menta = Menta(2018, 0.3F)
                it("No da Semillas") {
                    menta.daSemillas().shouldBeFalse()
                }
            }
        }
        describe("Soja") {
            describe("Soja vieja y pequeña") {
                val soja = Soja(2007, 0.1F)
                it("horas de sol") {
                    soja.horasDeSolQueTolera().shouldBe(6)
                }
                it("No es fuerte") {
                    soja.esFuerte().shouldBeFalse()
                }
                it("No da semillas") {
                    soja.daSemillas().shouldBeFalse()
                }
            }
            describe("Soja mediana") {
                val soja = Soja(2009, 0.6F)
                it("horas de sol") {
                    soja.horasDeSolQueTolera().shouldBe(7)
                }
                it("No da semillas") {
                    soja.daSemillas().shouldBeFalse()
                }
            }
            describe("Soja alta") {
                val soja = Soja(2009, 1.6F)
                it("horas de sol") {
                    soja.horasDeSolQueTolera().shouldBe(9)
                }
                it("Da semillas") {
                    soja.daSemillas().shouldBeTrue()
                }
            }
        }
        describe("Soja Transgénica") {
            val soja = SojaTransgenica(2007, 0.4F)
            it("horas de sol") {
                soja.horasDeSolQueTolera().shouldBe(12)
            }
            it("Da semillas") {
                soja.daSemillas().shouldBeFalse()
            }
            it("Es fuerte") {
                soja.esFuerte().shouldBeTrue()
            }
        }
    }
    describe ("Parcela"){
        val parcela = Parcela(20,1,8)
        it("superficie"){
            parcela.superficie().shouldBe(20)
        }
        it("cant max de plantas"){
            parcela.cantidadMaximaPlantas().shouldBe(4)
        }
        describe("Parcela con plantas"){
            var i = 3
            while (i>0){
                parcela.plantar(Soja(2008,1F))
                i -= 1
            }
            it ("no tiene complicaciones"){
                parcela.parcelaTieneComplicaciones().shouldBeFalse()
            }
            it("Error por superar máximo") {
                parcela.plantar(Soja(2008, 1F))
                shouldThrowAny { parcela.plantar(Soja(2008, 1F)) }
            }
            it("Error por mucho sol"){
                shouldThrowAny {parcela.plantar(Menta(2009,0.1F))}
            }

        }


    }
    describe("Agricultoras"){
        val parcela1 = Parcela(20,1,6)

        val parcela2= Parcela(20,1,6)
        var i = 3
        while (i>0){
            parcela2.plantar(Menta(2008,1F))
            i -= 1
        }
        val agricultora = Agricultora(listOf(parcela1,parcela2))
        it("Alguna parcela sin plantas"){
            agricultora.parcelasSemilleras().shouldBe(mutableListOf(parcela2))
        }
        it("Todas las parcelas con plantas"){
            parcela1.plantar(Menta(2006,0.1F))
            agricultora.parcelasSemilleras().shouldBe(mutableListOf(parcela2))
        }
        it("Plantar estrategicamente"){
            agricultora.plantarEstrategicamente(Menta(2008,1F))
            parcela1.plantas.size.shouldBe(1)
        }
    }
})
