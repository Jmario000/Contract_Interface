package model.services;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {

	public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public ContractService() {
	}

	public ContractService(OnlinePaymentService paymentService) {
		this.paymentService = paymentService;
	}

	private OnlinePaymentService paymentService;

	List<Installment> list = new ArrayList<>();

	public void processContract(Contract contract, int months) {
		double installmentValue;
		for (int i = 1; i <= months; i++) {
			installmentValue = paymentService
					.paymentFee(paymentService.interest(contract.getTotalValue() / (double) months, i));
			list.add(new Installment(contract.getDate().plusMonths(i), installmentValue));
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Installments: \n");
		for (Installment l : list) {
			sb.append(l.getDueDate().format(dtf)).append(" - ").append(String.format("%.2f", l.getAmount()))
					.append("\n");
		}
		return sb.toString();
	}
}
