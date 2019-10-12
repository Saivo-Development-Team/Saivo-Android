package com.saivo.recommendo.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.saivo.recommendo.R
import com.saivo.recommendo.data.model.infrastructure.Token
import com.saivo.recommendo.view.viewmodel.ViewModelFactory
import com.saivo.recommendo.view.viewmodel.auth.IServerAuth
import com.saivo.recommendo.view.viewmodel.auth.IServerAuthListener
import com.saivo.recommendo.view.viewmodel.auth.ServerViewModel
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


@ExperimentalStdlibApi
class SplashFragment : CoroutineFragment(), KodeinAware, IServerAuthListener {
    override val kodein: Kodein by closestKodein()
    private lateinit var serverViewModel: IServerAuth
    private val viewModelFactory: ViewModelFactory by instance()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        serverViewModel = ViewModelProvider(this, viewModelFactory).get(ServerViewModel::class.java)
        serverViewModel.addServerAuthListener(this)
        launch { serverViewModel.init() }.invokeOnCompletion {
            Navigation.findNavController(view).navigate(R.id.to_login_action)
        }
    }

    override fun onInit() {
        setLoadingText("Checking Access...")
    }

    override fun onAccess() {
        setLoadingText("Getting Access...")
    }

    override fun onRegister() {
        setLoadingText("Registering Device...")
    }

    override fun onRegistered() {
        setLoadingText("Got Access")
    }

    override fun onCreateToken(block: () -> Any) {
        setLoadingText("All Done!")
    }

    override fun onTokenCreated(block: ((token: Token) -> Any) -> Unit) {
        setLoadingText("All Done!")
    }


    private fun setLoadingText(text: String) {
        launch(this.coroutineContext) {
            splash_loading_text.text = text
        }
    }
}
