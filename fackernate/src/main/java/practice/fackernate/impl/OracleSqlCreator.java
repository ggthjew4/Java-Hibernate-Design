package practice.fackernate.impl;
import practice.fackernate.SqlCreator;
import practice.fackernate.mapping.MappingObject;
import practice.fackernate.mapping.Property;


public class OracleSqlCreator implements SqlCreator {
	
	@Override
	public String createSaveSql(MappingObject obj) {
		StringBuilder sb = new StringBuilder("insert into ");
		sb.append(obj.getTableName() + "(");
		sb.append(obj.getId().getColumn());
		for (Property property : obj.getProperties()) {
			sb.append(", " + property.getColumn());
		}
		sb.append(") values(");
		
		for (int i = 0 ; i < obj.getProperties().size() + 1 ; i++) {
			if (i == 0) {
				sb.append(" ?");
			} else {
				sb.append(", ?");
			}
		}
		sb.append(");");
		return sb.toString();
	}

	@Override
	public String createUpdateSql(MappingObject obj) {
		StringBuilder sb = new StringBuilder("update " + obj.getTableName() + " set ");
		for (int i = 0 ; i < obj.getProperties().size(); i++) {
			if (i == 0) {
				sb.append(obj.getProperties().get(i).getColumn() + " = ? ");
			} else {
				sb.append(", " + obj.getProperties().get(i).getColumn() + " = ? ");
			}
		}
		sb.append(" where " + obj.getId().getColumn() + " = ? ;");
		return sb.toString();
	}

	@Override
	public String createDeleteSql(MappingObject obj) {
		StringBuilder sb = new StringBuilder("delete from " + obj.getTableName());
		sb.append(" where " + obj.getId().getColumn() + " = ? ;");
		return sb.toString();
	}

	@Override
	public String createGetSql(MappingObject obj) {
		StringBuilder sb = new StringBuilder("select ");
		sb.append(obj.getId().getColumn());
		for (Property property : obj.getProperties()) {
			sb.append(", " + property.getColumn());
		}
		sb.append(" from " + obj.getTableName());
		sb.append(" where " + obj.getId().getColumn() + " = ? ;");
		return sb.toString();
	}
}
