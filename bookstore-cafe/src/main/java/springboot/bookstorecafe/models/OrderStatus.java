package springboot.bookstorecafe.models;

public enum OrderStatus {

	
	W_TRAKCIE("W trakcie"), GOTOWE_DO_ODBIORU("Gotowe do odbioru"), ODEBRANE("Odebrane"), OCZEKIWANIE_NA_DOSTAWE("Oczekiwanie na dostawę");
	
	  private final String displayStatus;

	
	    OrderStatus(String displayStatus) {
	        this.displayStatus = displayStatus;
	    }

	    public String getDisplayStatus() {
	        return displayStatus;
	    }
	    
	    public static OrderStatus fromDisplayStatus(String displayStatus) {
	        for (OrderStatus status : values()) {
	            if (status.getDisplayStatus().equalsIgnoreCase(displayStatus)) {
	                return status;
	            }
	        }
	        System.out.println("Nieznany displayName: " + displayStatus); // Logowanie do debugowania
	        throw new IllegalArgumentException("Nieznany displayName: " + displayStatus);
	    }

   
}
