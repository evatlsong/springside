package org.springside.examples.showcase.solr;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springside.examples.showcase.common.entity.Post;

public class PostSolrDao {

	private SolrServer solrServer;

	public List<Post> queryPost(String queryString, int start, int returnRows, String sortField, ORDER sortOrder)
			throws SolrServerException {
		SolrQuery solrQuery = new SolrQuery(queryString);

		if (start >= 0)
			solrQuery.setStart(start);

		if (returnRows >= 0)
			solrQuery.setRows(returnRows);

		if (StringUtils.isNotBlank(sortField))
			solrQuery.setSortField(sortField, sortOrder);

		QueryResponse response = solrServer.query(solrQuery);
		return response.getBeans(Post.class);
	}

	public void savePost(Post post) throws IOException, SolrServerException {
		Assert.hasText(post.getId());
		solrServer.addBean(post);
		solrServer.commit();
	}

	public void deletePost(String queryString) throws SolrServerException, IOException {
		solrServer.deleteByQuery(queryString);
		solrServer.commit();
	}

	@Autowired
	public void setSolrServer(SolrServer solrServer) {
		this.solrServer = solrServer;
	}
}
