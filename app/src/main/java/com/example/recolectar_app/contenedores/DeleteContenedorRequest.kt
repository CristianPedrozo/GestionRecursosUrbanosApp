
import com.example.recolectar_app.contenedores.Contenedor
import java.util.ArrayList

class DeleteContenedorRequest {
    var actionType : String = "delete"
    var entities = ArrayList<Contenedor>()

    fun addEntitie(entitie: Contenedor){
        entities.add(entitie)
    }
}