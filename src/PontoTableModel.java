import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class PontoTableModel extends AbstractTableModel {

	private List<Ponto> linhas;
	private String [] colunas = {"Dia", "Entrada", "Saida Pausa", "Entrada Pausa", "Saida", "Chave", "Data","Hrs Trab." ,"Noturno", "Diurno"};
	
	private static final int DIA 		= 0;
	private static final int ENTRADA 	= 1;
	private static final int SAIDAP 	= 2;
	private static final int ENTRADAP 	= 3;
	private static final int SAIDA 		= 4;
	private static final int CHAVE 		= 5;
	private static final int DATA 		= 6;
	private static final int HRSTRAB 	= 7;
	private static final int NOTURNO 	= 8;
	private static final int DIURNO 	= 9;
	
	
	public PontoTableModel() {//cria lista vazia
		linhas = new ArrayList<Ponto>();
	}
	
	public PontoTableModel(List<Ponto>ponto) {//cria lista com valor
		linhas = new ArrayList<Ponto>(ponto);
	}
	
	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public int getRowCount() {
		return linhas.size();
	}
	
	public String getColumnName(int columnIndex) {
		return (String)colunas[columnIndex];
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		
		switch (columnIndex) {
		case DIA:
			return String.class;
		case ENTRADA:
			return String.class;
		case SAIDAP:
			return String.class;
		case ENTRADAP:
			return String.class;
		case SAIDA:
			return String.class;
		case CHAVE:
			return String.class;
		case DATA:
			return String.class;
		case HRSTRAB:
			return String.class;
		case NOTURNO:
			return String.class;
		case DIURNO:
			return String.class;

		default:
			throw new IndexOutOfBoundsException("Column index out of bound");
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Ponto ponto = linhas.get(rowIndex);
		
		switch (columnIndex) {
		case DIA:
			return (String)ponto.getDia();
		case ENTRADA:
			return (String)ponto.getEntrada();
		case SAIDAP:
			return (String)ponto.getSaidap();
		case ENTRADAP:
			return (String)ponto.getEntradap();
		case SAIDA:
			return (String)ponto.getSaida();
		case CHAVE:
			return (String)ponto.getChave();
		case DATA:
			return (String)ponto.getData();
		case HRSTRAB:
			return (String)ponto.getHrsTrab();
		case NOTURNO:
			return (String)ponto.getIndNot();
		case DIURNO:
			return (String)ponto.getIndDia();

		default:
			throw new IndexOutOfBoundsException("Column index out of bound");
		}
		
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Ponto ponto = linhas.get(rowIndex);
		
		switch (columnIndex) {
		case DIA:
			ponto.setDia((String)aValue);
			break;
		case ENTRADA:
			ponto.setEntrada((String)aValue);
			break;
		case SAIDAP:
			ponto.setSaidap((String)aValue);
			break;
		case ENTRADAP:
			ponto.setEntradap((String)aValue);
			break;
		case SAIDA:
			ponto.setSaida((String)aValue);
			break;
		case CHAVE:
			ponto.setChave((String)aValue);
			break;
		case DATA:
			ponto.setData((String)aValue);
			break;
		case HRSTRAB:
			ponto.setHrsTrab((String)aValue);
			break;
		case NOTURNO:
			ponto.setIndNot((String)aValue);
			break;
		case DIURNO:
			ponto.setDia((String)aValue);
			break;

		default:
			throw new IndexOutOfBoundsException("Column index out of bound");
		}
		
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	public Ponto getPonto(int indiceLinha) {
		return linhas.get(indiceLinha);
	}
	
	public void setPonto(Ponto ponto) {
		linhas.add(ponto);
		int ultimoIndice = this.getRowCount() - 1;
		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}
	
	public void removePonto(int indiceLinha) {
		linhas.remove(indiceLinha);
		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}
	
	public void addListaPonto(List<Ponto> ponto) {
		int indice = this.getRowCount();
		linhas.addAll(ponto);
		fireTableRowsInserted(indice, indice+ponto.size());
	}
	
	public void limpar() {
		linhas.clear();
		fireTableDataChanged();
	}

}
