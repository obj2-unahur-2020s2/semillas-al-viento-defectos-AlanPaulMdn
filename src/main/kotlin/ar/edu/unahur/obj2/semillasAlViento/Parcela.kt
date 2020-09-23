package ar.edu.unahur.obj2.semillasAlViento

data class Parcela(val ancho: Int, val largo: Int, val horasSolPorDia: Int) {
  val plantas = mutableListOf<Planta>()

  fun superficie() = ancho * largo
  fun cantidadMaximaPlantas() =
    if (ancho > largo) this.superficie() / 5 else this.superficie() / 3 + largo

  fun plantar(planta: Planta) {
    if (plantas.size == this.cantidadMaximaPlantas()) {
      throw Exception("Ya no hay lugar en esta parcela")
    } else if (horasSolPorDia >= planta.horasDeSolQueTolera() + 2) {
      throw Exception("No se puede plantar esto acá, se va a quemar")
    } else {
      plantas.add(planta)
    }
  }
  fun esSemillera()= //Pongo ternario porque todavía no entiendo los operadores
    if (plantas.isEmpty()) false else plantas.all{ it.daSemillas() }

  fun parcelaTieneComplicaciones() =
          plantas.any { it.horasDeSolQueTolera() < horasSolPorDia }
}

class Agricultora(val parcelas: List<Parcela>) {

  fun parcelasSemilleras() =
    parcelas.filter { it.esSemillera() }

  fun parcelaEstrategica()=
          parcelas.maxBy { it.cantidadMaximaPlantas() - it.plantas.size }!!

  fun plantarEstrategicamente(planta: Planta) {
    this.parcelaEstrategica().plantar(planta)
  }
}
