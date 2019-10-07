package com.saivo.recommendo.view.fragment.pages


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.saivo.recommendo.R
import com.saivo.recommendo.view.fragment.CoroutineFragment
import com.saivo.recommendo.view.viewmodel.user.IUserViewModel
import com.saivo.recommendo.view.viewmodel.user.UserViewModel
import com.saivo.recommendo.view.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.layout_profile_banner.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : CoroutineFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private lateinit var userViewModel: IUserViewModel
    private val viewModelFactory: ViewModelFactory by instance()
    //
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var appCompatActivity: AppCompatActivity
    private lateinit var dashboardNavController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)

        launch {
            userViewModel.userData.await().observe(
                this@DashboardFragment, Observer {
                    fullname.text = "${it.firstname} ${it.lastname}"
                    email_text_view.text = it.email
                }
            )
        }

        setupNavController()
        setupMaterialToolbar()
        setupWithNavController(navigation_view, dashboardNavController)
        setupWithNavController(materialToolbar, dashboardNavController, drawerLayout)
    }

    private fun setupMaterialToolbar() {
        appCompatActivity = (activity as AppCompatActivity)
        appCompatActivity.setSupportActionBar(materialToolbar)
        appCompatActivity.title = dashboardNavController.currentDestination?.label
        drawerToggle = ActionBarDrawerToggle(
            appCompatActivity, drawerLayout, materialToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
    }

    private fun setupNavController() {
        dashboardNavController = (childFragmentManager
            .findFragmentById(R.id.dashboard_navigation_host) as NavHostFragment)
            .navController
    }
}
