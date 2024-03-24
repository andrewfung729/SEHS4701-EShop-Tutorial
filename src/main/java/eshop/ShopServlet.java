package eshop;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import eshop.model.DataManager;

@WebServlet(name = "ShopServlet", urlPatterns = "/shop")
public class ShopServlet extends HttpServlet implements jakarta.servlet.Servlet {
    private static final long serialVersionUID = 1L;

    public ShopServlet() {
        super();
    }

    public void init(ServletConfig config) throws ServletException {
        System.out.println("*** initializing controller servlet.");
        super.init(config);

        DataManager dataManager = new DataManager();
        dataManager.setDbURL(config.getInitParameter("dbURL"));
        dataManager.setDbUserName(config.getInitParameter("dbUserName"));
        dataManager.setDbPassword(config.getInitParameter("dbPassword"));

        ServletContext context = config.getServletContext();
        context.setAttribute("base", context.getContextPath() + AppConstants.BASE_URL);
        context.setAttribute("imageURL", AppConstants.IMAGE_URL);
        context.setAttribute("dataManager", dataManager);

        try {  // load the database JDBC driver
            Class.forName(config.getInitParameter("jdbcDriver"));
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String base = "/jsp/";
        String url = base + "index.jsp";
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "search":
                    url = base + "SearchOutcome.jsp";
                    break;
                case "selectCatalog":
                    url = base + "SelectCatalog.jsp";
                    break;
                case "bookDetails":
                    url = base + "BookDetails.jsp";
                    break;
                case "checkOut":
                    url = base + "Checkout.jsp";
                    break;
                case "orderConfirmation":
                    url = base + "OrderConfirmation.jsp";
                    break;
                default:
                    if (action.matches("(showCart|(add|update|delete)Item)")) url = base + "ShoppingCart.jsp";
                    break;
            }
        }
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
    }
}
