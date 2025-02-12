package snippet;

public class Snippet {
	<div class="customer-list">
	    <div class="customer-card" th:each="cliente : ${clienti}" onclick="window.location.href='/customer/detail?customerId=' + [[${cliente.id}]];">
	        <div class="customer-info">
	            <p><strong>Nome:</strong> <span th:text="${cliente.nome}"></span></p>
	            <p><strong>Cognome:</strong> <span th:text="${cliente.cognome}"></span></p>
	            <p><strong>Città:</strong> <span th:text="${cliente.citta}"></span></p>
	            <p><strong>Email:</strong> <span th:text="${cliente.email}"></span></p>
	        </div>
	    </div>
	</div>
	
}

