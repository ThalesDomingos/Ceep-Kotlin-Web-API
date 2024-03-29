package br.com.alura.ceep.webclient

import android.util.Log
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.webclient.model.NotaRequisicao
import br.com.alura.ceep.webclient.services.NotaService
import javax.security.auth.login.LoginException

private const val TAG = "NotaWebClient"

class NotaWebClient {

    private val notaService: NotaService =
        RetrofitInicializador().notaService

    suspend fun BuscaTodas(): List<Nota>? {
        return try {
            val notasResposta = notaService
                .buscaTodas()
            notasResposta.map { notaResposta ->
                notaResposta.nota
            }
        } catch (e: Exception) {
            Log.e(TAG, "BuscaTodas: ", e)
            null
        }
    }

    suspend fun salva(nota: Nota) : Boolean {
        try {
            val resposta = notaService.salva(nota.id, NotaRequisicao(
                titulo = nota.titulo,
                descricao = nota.descricao,
                imagem = nota.imagem
            ))
            return resposta.isSuccessful
        } catch (e: Exception) {
            Log.e(TAG, "Falha ao tentar salvar", e)
        }
        return false
    }

    suspend fun remove(id: String) : Boolean {
        try {
            val resposta = notaService.remove(id)
            return true
        } catch (e: Exception) {
            Log.e(TAG, "falha ao remover Nota", e)
        }
        return false
    }
}