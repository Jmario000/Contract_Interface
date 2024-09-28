package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JOptionPane;

import model.entities.Contract;
import model.services.ContractService;
import model.services.OnlinePaymentService;
import model.services.PaypalService;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		JOptionPane.showMessageDialog(null, "Enter contract data:");
		int number = Integer.parseInt(JOptionPane.showInputDialog("Number:"));
		LocalDate date = LocalDate.parse(JOptionPane.showInputDialog("Date (dd/MM/yyyy): "), dtf);
		double totalValue = Double.parseDouble(JOptionPane.showInputDialog("Contract value: $ "));
		int numberOfInstallments = Integer.parseInt(JOptionPane.showInputDialog("Number of installments:"));

		Contract obj = new Contract(number, date, totalValue);
		OnlinePaymentService paymentService = new PaypalService();
		ContractService service = new ContractService(paymentService);
		service.processContract(obj, numberOfInstallments);

		JOptionPane.showMessageDialog(null, service.toString());

	}

}
