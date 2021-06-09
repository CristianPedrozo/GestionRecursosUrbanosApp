package com.example.recolectar_app.mapa

import android.content.Context
import com.google.android.gms.maps.model.LatLng

object UtilidadesOrdenamiento {
    var puntosAOrdenar:MutableList<LatLng> = mutableListOf(
        LatLng(-34.5841907,-58.4013909),
        LatLng(-34.583682,-58.4020395),
        LatLng(-34.583715,-58.4028095),
        LatLng(-34.5847,-58.4035265),
        LatLng(-34.585232,-58.4039075),
        LatLng(-34.585804,-58.4035665),
        LatLng(-34.585912,-58.4036097),
        LatLng(-34.586312,-58.403286),
        LatLng(-34.586268,-58.4026185),
        LatLng(-34.586027,-58.402469),
        LatLng(-34.585462,-58.40163),
        LatLng(-34.583404,-58.4046354),
        LatLng(-34.583408,-58.405492),
        LatLng(-34.583505,-58.40708),
        LatLng(-34.584337,-58.4060145),
        LatLng(-34.584916,-58.405619),
        LatLng(-34.584168,-58.40852),
        LatLng(-34.584663,-58.409217),
        LatLng(-34.585364,-58.4066975),
        LatLng(-34.585727,-58.4068456),
        LatLng(-34.586045,-58.40721),
        LatLng(-34.5865556,-58.4063607),
        LatLng(-34.587024,-58.405206),
        LatLng(-34.587024,-58.404895),
        LatLng(-34.586392,-58.405065),
        LatLng(-34.585288,-58.409625),
        LatLng(-34.58575,-58.408496),
        LatLng(-34.586136,-58.408033),
        LatLng(-34.586051,-58.4071617),
        LatLng(-34.58666,-58.407366),
        LatLng(-34.587051,-58.4064447),
        LatLng(-34.586874,-58.406273),
        LatLng(-34.587435,-58.406139),
        LatLng(-34.587809,-58.405543),
        LatLng(-34.587817,-58.404907),
        LatLng(-34.587353,-58.404274),
        LatLng(-34.586271,-58.4097224),
        LatLng(-34.586824,-58.409112),
        LatLng(-34.586903,-58.4079327),
        LatLng(-34.586417,-58.407831),
        LatLng(-34.587449,-58.4080017),
        LatLng(-34.5877555,-58.4069941),
        LatLng(-34.587776,-58.4067775),
        LatLng(-34.587299,-58.4062165),
        LatLng(-34.586771,-58.4094315),
        LatLng(-34.58738,-58.410005),
        LatLng(-34.5880422,-58.4091442),
        LatLng(-34.587916,-58.4090085),
        LatLng(-34.587916,-58.4090085),
        LatLng(-34.588375,-58.409121),
        LatLng(-34.588569,-58.408252),
        LatLng(-34.588123,-58.407748),
        LatLng(-34.589017,-58.4077305),
        LatLng(-34.589397,-58.4074377),
        LatLng(-34.589237,-58.406818),
        LatLng(-34.587888,-58.4105666),
        LatLng(-34.588378,-58.4102635),
        LatLng(-34.588639,-58.4098067),
        LatLng(-34.58859,-58.409775),
        LatLng(-34.588285,-58.409501),
        LatLng(-34.588581,-58.4098847),
        LatLng(-34.588564,-58.4092135),
        LatLng(-34.588901,-58.4092405),
        LatLng(-34.589825,-58.40853),
        LatLng(-34.589949,-58.407817),
        LatLng(-34.588412,-58.410757),
        LatLng(-34.589307,-58.4105507),
        LatLng(-34.589091,-58.410229),
        LatLng(-34.589765,-58.4100297),
        LatLng(-34.589655,-58.409515),
        LatLng(-34.5904211,-58.4083827),
        LatLng(-34.590584,-58.408699),
        LatLng(-34.590827,-58.4092469),
        LatLng(-34.590071,-58.413198),
        LatLng(-34.590627,-58.4113315),
        LatLng(-34.590563,-58.4108885),
        LatLng(-34.589874,-58.4103115),
        LatLng(-34.590944,-58.4108025),
        LatLng(-34.590913,-58.4100725),
        LatLng(-34.590482,-58.4095875),
        LatLng(-34.591417,-58.4100735),
        LatLng(-34.5914928,-58.4087814),
        LatLng(-34.591082,-58.4087965),
        LatLng(-34.590771,-58.4128626),
        LatLng(-34.591394,-58.4130215),
        LatLng(-34.59195,-58.413182),
        LatLng(-34.591932,-58.41256),
        LatLng(-34.591168,-58.411948),
        LatLng(-34.592379,-58.4119753),
        LatLng(-34.592587,-58.4117695),
        LatLng(-34.593310, -58.411125),
        LatLng(-34.593540, -58.410605),
        LatLng(-34.593439,-58.4106245),
        LatLng(-34.592989,-58.4103835),
        LatLng(-34.5926095,-58.4131135),
        LatLng(-34.592813,-58.4138665),
        LatLng(-34.59326,-58.4138125),
        LatLng(-34.593517,-58.4134845),
        LatLng(-34.593392,-58.4130945),
        LatLng(-34.593043,-58.4128385),
        LatLng(-34.593831,-58.4130975),
        LatLng(-34.593788,-58.4123034),
        LatLng(-34.593362,-58.4123045),
        LatLng(-34.593274,-58.4143165),
        LatLng(-34.593755,-58.415265),
        LatLng(-34.593788,-58.4123034),
        LatLng(-34.595197, -58.412954),
        LatLng(-34.594185, -58.412333),
        LatLng(-34.593508, -58.411868),
        LatLng(-34.593282, -58.411174),
        LatLng(-34.593502, -58.410676),
        LatLng(-34.594060, -58.410431),
        LatLng(-34.594864, -58.410854),
        LatLng(-34.595696, -58.412383),
        LatLng(-34.596063, -58.411901),
        LatLng(-34.595847, -58.411407),
        LatLng(-34.594369, -58.414537),
        LatLng(-34.594817, -58.414733),
        LatLng(-34.595301, -58.414919),
        LatLng(-34.595594, -58.414492),
        LatLng(-34.595371, -58.413752),
        LatLng(-34.594940, -58.413631),
        LatLng(-34.595599, -58.413110),
        LatLng(-34.596067, -58.415236),
        LatLng(-34.596721, -58.415142),
        LatLng(-34.596745, -58.414506),
        LatLng(-34.596432, -58.414138),
        LatLng(-34.595884, -58.413934),
        LatLng(-34.596798, -58.413241),
        LatLng(-34.596355, -58.412894),
        LatLng(-34.595774, -58.412763),
        LatLng(-34.596818, -58.412707),
        LatLng(-34.596855, -58.412026),
        LatLng(-34.596515, -58.411699),
        LatLng(-34.597104, -58.415706),
        LatLng(-34.597863, -58.415358),
        LatLng(-34.597514, -58.414522),
        LatLng(-34.597031, -58.414339),
        LatLng(-34.597905, -58.414200),
        LatLng(-34.597936, -58.413519),
        LatLng(-34.597437, -58.413142),
        LatLng(-34.597958, -58.412710),
        LatLng(-34.597416, -58.411864)
    )
    var arrayDeRequest:MutableList<unRequest> = ArrayList()
    var puntosOrdenados:MutableList<PuntoDistancia> = ArrayList()
    var controlRequest=0

    fun realizarOrden (inicio:LatLng, contexto:Context){
        var aux =1
        var auxUnRequest:unRequest= unRequest()
        for (i in 0 until puntosAOrdenar.size){
            var auxPuntoDistancia:PuntoDistancia= PuntoDistancia()
            auxPuntoDistancia.punto= puntosAOrdenar[i]
            auxUnRequest.puntos.add(auxPuntoDistancia)
            if (i+1==25*aux){
                arrayDeRequest.add(auxUnRequest)
                auxUnRequest= unRequest()
                aux++
            }else{
                if (i+1== puntosAOrdenar.size){
                    arrayDeRequest.add(auxUnRequest)
                }
            }
        }
        for (i in 0 until arrayDeRequest.size){
            arrayDeRequest[i].obtenerURLApi(inicio)
            arrayDeRequest[i].obtenerDistancias(contexto)
        }

    }
    fun obtenerPuntoMasCercano(){
        var auxPtoMasCercano:PuntoDistancia= arrayDeRequest[0].ptoMasCercano
        var indexAEliminar= arrayDeRequest[0].indexPtoMasCercano
        for(i in 0 until arrayDeRequest.size){
            if(arrayDeRequest[i].ptoMasCercano.distancia<auxPtoMasCercano.distancia){
                auxPtoMasCercano= arrayDeRequest[i].ptoMasCercano
                indexAEliminar=25*i+ arrayDeRequest[i].indexPtoMasCercano
            }
        }

        puntosOrdenados.add(auxPtoMasCercano)
        puntosAOrdenar.removeAt(indexAEliminar)
        arrayDeRequest =ArrayList()
        controlRequest =0
    }
}