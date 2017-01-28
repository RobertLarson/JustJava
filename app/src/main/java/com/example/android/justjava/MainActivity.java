/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 */

package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.os.Build.VERSION_CODES.N;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText nameEditText = (EditText) findViewById(R.id.name_view);
        String name = nameEditText.getText().toString();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String orderSummary = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        String emailSubject = getString(R.string.order_summary_email_subject, name);
        composeEmail(emailSubject, orderSummary);

    }

    /**
     * Compose an email with subject and text parameters
     * @param subject is the subject of the email
     * @param emailText is the text of the email body.
     */
    public void composeEmail(String subject, String emailText) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, emailText);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No email app", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /**
     *  Calculates the price of the coffee order/
     * @param hasWhippedCream is whether or not whipped is added.
     * @param hasChocolate is whether or not the chocolated is added.
     * @return the total price of the order
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate)
    {
        final int priceOfWhippedCreamTopping = 1;
        final int priceOfChocolateTopping = 2;
        int priceOfOne = 5;

        if(hasWhippedCream) {
            priceOfOne += priceOfWhippedCreamTopping;
        }

        if(hasChocolate) {
            priceOfOne += priceOfChocolateTopping;
        }

        return quantity * priceOfOne;
    }

    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate,
                                      String name)
    {
        String orderSummary = getString(R.string.order_summary_name, name) + "\n";
        orderSummary += getString(R.string.order_summary_whipped_cream, hasWhippedCream) + "\n";
        orderSummary += getString(R.string.order_summary_chocolate, hasChocolate) + "\n";
        orderSummary += getString(R.string.order_summary_quantity, quantity) + "\n";
        orderSummary += getString(R.string.order_summary_price, "$" + Integer.toString(price)) + "\n";
        orderSummary += getString(R.string.thank_you);

        return orderSummary;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void incrementQuantity(View view) {
        if(quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there is nothing left to do.
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void decrementQuantity(View view) {
        if(quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there is nothing left to do.
            return;
        }

        quantity--;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void whippedCreamSelected(View view) {

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}