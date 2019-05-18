package ip.designpattern.behavioural.mediator;

public class TradeRequest {
	
	private String stockId;
	private TRADE_TYPE tradeType;
	private StockMarketUser stockHolderUser;
	private double bitPrice;
	private boolean isProcessed = false;
	private PROCESSED_STATUS status;
	private double processedPrice;
	
	public TradeRequest(String stockId, TRADE_TYPE tradeType, StockMarketUser stockHolderUser) {
		this.stockId = stockId;
		this.tradeType = tradeType;
		this.stockHolderUser = stockHolderUser;
	}
	
	public StockMarketUser getStockTrader() {
		return stockHolderUser;
	}

	public boolean isProcessed() {
		return isProcessed;
	}

	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	public PROCESSED_STATUS getStatus() {
		return status;
	}

	public void setStatus(PROCESSED_STATUS status) {
		this.status = status;
	}

	public double getProcessedPrice() {
		return processedPrice;
	}

	public void setProcessedPrice(double processedPrice) {
		this.processedPrice = processedPrice;
	}

	public TRADE_TYPE getTradeType() {
		return tradeType;
	}

	public String getStockId() {
		return stockId;
	}

	public double getBitPrice() {
		return bitPrice;
	}

	public void setBitPrice(double bitPrice) {
		this.bitPrice = bitPrice;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TradeRequest){
			TradeRequest stockTrade = (TradeRequest)obj;
			if(stockTrade.getStockId().equalsIgnoreCase(this.stockId)
					&& stockTrade.getTradeType() == this.tradeType){
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "TradeRequest [stockId=" + stockId + ", tradeType=" + tradeType + ", stockHolderUser=" + stockHolderUser
				+ ", bitPrice=" + bitPrice + ", isProcessed=" + isProcessed + ", status=" + status + ", processedPrice="
				+ processedPrice + "]";
	}


	enum TRADE_TYPE {
		BUY(0), SELL(1);
		private int type;
		private TRADE_TYPE(int type){
			this.type = type;
		}
		public static TRADE_TYPE getType(int type){
			if(type == 0){
				return TRADE_TYPE.BUY;
			}else if(type == 1){
				return TRADE_TYPE.SELL;
			}else{
				return null;
			}
		}
		public int getType() {
			return type;
		}
	}
	
	enum PROCESSED_STATUS {
		SUCCESS, FAIL;
	}

}
