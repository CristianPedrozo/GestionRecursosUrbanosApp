
import com.example.recolectar_app.entities.Camion
import java.util.ArrayList

class DeleteCamionRequest {
    var actionType : String = "delete"
    var entities = ArrayList<Camion>()

    fun addEntitie(entitie: Camion){
        entities.add(entitie)
    }
}