package nspages;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class Test_exclude extends Helper {
	// Note that the "global_exclude" conf is implicitly tested by each of those tests
	// since each would see the page "c_template" if this feature doesn't work

	@Test
	public void withoutOption(){
		generatePage("excludepage:start", "<nspages>");

		List<InternalLink> expectedLinks = new ArrayList<InternalLink>();
		expectedLinks.add(new InternalLink("excludepage:p1", "p1"));
		expectedLinks.add(new InternalLink("excludepage:p2", "p2"));
		expectedLinks.add(new InternalLink("excludepage:start", "start"));

		assertSameLinks(expectedLinks);
	}

	@Test
	public void optionAlone(){
		generatePage("excludepage:start", "<nspages -exclude>");

		List<InternalLink> expectedLinks = new ArrayList<InternalLink>();
		expectedLinks.add(new InternalLink("excludepage:p1", "p1"));
		expectedLinks.add(new InternalLink("excludepage:p2", "p2"));

		assertSameLinks(expectedLinks);
	}

	@Test
	public void optionWithArg(){
		generatePage("excludepage:start", "<nspages -exclude:p1 -exclude:p2>");

		List<InternalLink> expectedLinks = new ArrayList<InternalLink>();
		expectedLinks.add(new InternalLink("excludepage:start", "start"));

		assertSameLinks(expectedLinks);
	}

	@Test
	public void legacySyntax(){
		generatePage("excludepage:start", "<nspages -exclude:[start p1]>");

		List<InternalLink> expectedLinks = new ArrayList<InternalLink>();
		expectedLinks.add(new InternalLink("excludepage:p2", "p2"));

		assertSameLinks(expectedLinks);
	}

	@Test
	public void excludeSelfWithRecursion(){
		generatePage("exclude_and_recursion:p1", "<nspages -exclude -r>");

		List<InternalLink> expectedLinks = new ArrayList<InternalLink>();
		expectedLinks.add(new InternalLink("exclude_and_recursion:sub:p1", "p1"));

		assertSameLinks(expectedLinks);
	}

	@Test
	public void noRegForBugReport143(){
		// This test just ensure that we have no warning when the line is parsed.
		// The issue comes from the trailing whitespace at the end of the "-exclude:[...]" block
		generatePage("excludepage:start", "<nspages -exclude:[start wiki: syntax ]>");
	}
}
